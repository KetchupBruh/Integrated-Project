package sit.int221.mydb.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.mydb.dto.*;
import sit.int221.mydb.entities.Event;
import sit.int221.mydb.entities.Eventcategory;
import sit.int221.mydb.entities.User;
import sit.int221.mydb.repositories.EventRepository;
import sit.int221.mydb.repositories.EventcategoryRepository;
import sit.int221.mydb.repositories.UserRepository;
import sit.int221.mydb.utils.Interval;
import sit.int221.mydb.utils.ListMapper;
import sit.int221.mydb.utils.ResponseMessage;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EventService {
    private Interval interval;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private EventcategoryRepository eventcategoryRepository;

    @Autowired
    FilesStorageService storageService;

    public List<EventAllDto> getEventAll() {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        if(roleMail.getAuthorities().toString().equals("[ROLE_student]")){
            List<Event> eventAllList = eventRepository.findAllByBookingEmail(roleMail.getName());
            return listMapper.mapList(eventAllList, EventAllDto.class, modelMapper);
        }

        if(roleMail.getAuthorities().toString().equals("[ROLE_lecturer]")){
            User user = userRepository.findByUserEmail(roleMail.getName());
            List<Event> eventAllList = eventRepository.findAllByEventCategoryOwner(user.getId());
            return listMapper.mapList(eventAllList, EventAllDto.class, modelMapper);
        }

        List<Event> eventAllList = eventRepository.findAll(Sort.by("eventStartTime").descending());
        return listMapper.mapList(eventAllList, EventAllDto.class, modelMapper);
    }

    public EventDetailDto getDetail(Integer id) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        if(roleMail.getAuthorities().toString().equals("[ROLE_lecturer]")){
            User user = userRepository.findByUserEmail(roleMail.getName());
            Event eventLec = eventRepository.findEventByEventCategoryOwner(id,user.getId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.FORBIDDEN));
            return modelMapper.map(eventLec, EventDetailDto.class);
        }

        Event event = eventRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does't exist !!"));

        if(roleMail.getAuthorities().toString().equals("[ROLE_student]")){
            if(!roleMail.getName().equals(event.getBookingEmail())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }

        storageService.load(event.getFile());

        return modelMapper.map(event, EventDetailDto.class);
    }

    //post dto validate spring boot
    public Event create(EventScheduleDto eventNew){
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        Eventcategory eventCat = eventcategoryRepository.findById(eventNew.getEventCategory_eventCategoryId()).orElseThrow(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        for(EventAllDto ev : getEventAll()){
            Instant start = eventNew.getEventStartTime();
            Instant end = eventNew.getEventStartTime().plus(eventNew.getEventDuration(), ChronoUnit.MINUTES);
            Instant checkStart = ev.getEventStartTime();
            Instant checkEnd = ev.getEventStartTime().plus(ev.getEventDuration(), ChronoUnit.MINUTES);
            interval.isOverlap(start,end,checkStart,checkEnd);
        }

        Event event = modelMapper.map(eventNew, Event.class);

        //file upload
        if(eventNew.getFile() != null) {
            try {
                storageService.save(eventNew.getFile());
            } catch (Exception e) {
                String messageFile = "Could not upload the file: " + eventNew.getFile().getOriginalFilename() + "!";
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageFile);
            }
        }

        //name file to origin name
        if(eventNew.getFile() != null){
            event.setFile(eventNew.getFile().getOriginalFilename());
        }

        if(roleMail.getAuthorities().toString().equals("[ROLE_student]")){
            if(!roleMail.getName().equals(event.getBookingEmail())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email not same");
            }
        }
        JavaMailSenderImpl mailSender =new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("oasipsy4.tnt@gmail.com");
        mailSender.setPassword("zprmarzydjstdgja");

        Properties properties =new Properties();
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        mailSender.setJavaMailProperties(properties);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("oasipsy4.tnt@gmail.com");
        message.setTo(event.getBookingEmail());
        message.setSubject("Event has been created.");
        message.setText("Booking name: " + '\"' + event.getBookingName() + '\"' + "\n" +
                        "Event Category:" + '\"' + eventCat.getEventCategoryName() + '\"' + "\n" +
                        "Event duration: " + '\"' + event.getEventDuration() + '\"' + "\n" +
                        "Event start time: "+ '\"' + event.getEventStartTime() + '\"' + "\n" +
                        "Event notes: " + '\"' + event.getEventNotes() + '\"' + "\n");
        mailSender.send(message);

        return eventRepository.saveAndFlush(event);
    }

    //put dto Ver.4
    public Event editEvent(EventUpdateDto editEvent, Integer eventId) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();

        Event event= eventRepository.findById(eventId).orElseThrow(()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        for(EventAllDto ev : getEventAll()){
            if (!ev.getEventId().equals(eventId)) {
                Instant start = ev.getEventStartTime();
                Instant end = ev.getEventStartTime().plus(ev.getEventDuration(), ChronoUnit.MINUTES);
                Instant checkStart = event.getEventStartTime();
                Instant checkEnd = event.getEventStartTime().plus(event.getEventDuration(), ChronoUnit.MINUTES);
                interval.isOverlap(start, end, checkStart, checkEnd);
            }
        }

//        //remove old file and add new file upload
//        if(editEvent.getFile() != null) {
//            System.out.println(event.getFile());
//            System.out.println(editEvent.getFile().getOriginalFilename());
//            System.out.println("1");
//
//            if(editEvent.getFile().isEmpty()) {
//                storageService.deleteFile(event.getFile());
//                event.setFile(null);
//                System.out.println("2");
//            }
//
//            else{
//                storageService.deleteFile(event.getFile());
//                System.out.println("3");
//                storageService.save(editEvent.getFile());
//                event.setFile(editEvent.getFile().getOriginalFilename());
////                try {
////                    storageService.save(editEvent.getFile());
////                } catch (Exception e) {
////                    String messageFile = "Could not upload the file: " + editEvent.getFile().getOriginalFilename() + "!";
////                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageFile);
////                }
//            }
//        }

        if(roleMail.getAuthorities().toString().equals("[ROLE_student]")){
            if(!roleMail.getName().equals(event.getBookingEmail())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }

        //remove old file and add new file upload
        if(editEvent.getFile() != null) {

            if(editEvent.getFile().isEmpty()) {
                storageService.deleteFile(event.getFile());
                event.setFile(null);
            }

            else{
                storageService.deleteFile(event.getFile());
                storageService.save(editEvent.getFile());
                event.setFile(editEvent.getFile().getOriginalFilename());
//                try {
//                    storageService.save(editEvent.getFile());
//                } catch (Exception e) {
//                    String messageFile = "Could not upload the file: " + editEvent.getFile().getOriginalFilename() + "!";
//                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageFile);
//                }
            }
        }

        Event e = modelMapper.map(event,Event.class);
        return eventRepository.saveAndFlush(e);
    }

    //delete event
    public void deleteEventId(@PathVariable Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(()->new RuntimeException(eventId+ " does not exist !!!"));

        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();
        if(roleMail.getAuthorities().toString().equals("[ROLE_student]")){
            if(!roleMail.getName().equals(event.getBookingEmail())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }

        if(event.getFile() != null){
            storageService.deleteFile(event.getFile());
        }
        eventRepository.deleteById(eventId);
    }

    //get by category id
    public List<EventAllDto> getEventListByCategoryId(@PathVariable Integer eventCategory_eventCategoryId) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();
        if(roleMail.getAuthorities().toString().equals("[ROLE_student]")){
            List<Event> eventAllList = eventRepository.findAllByBookingEmail(roleMail.getName());
            return listMapper.mapList(eventAllList, EventAllDto.class, modelMapper);
        }

        List<Event> eventList = eventRepository.findAllByEventCategoryId(eventCategory_eventCategoryId);
        return listMapper.mapList(eventList, EventAllDto.class, modelMapper);
    }

    //get by date time is past
    public List<EventAllDto> getEventDatePast() {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();
        if(roleMail.getAuthorities().toString().equals("[ROLE_student]")){
            LocalDateTime past = LocalDateTime.now(ZoneId.systemDefault());
            List<Event> eventList = eventRepository.findAllByEventStudentPast(roleMail.getName(), past);
            return listMapper.mapList(eventList, EventAllDto.class, modelMapper);
        }

        LocalDateTime past = LocalDateTime.now(ZoneId.systemDefault());
        List<Event> eventList = eventRepository.findAllByEventPast(past);
        return listMapper.mapList(eventList, EventAllDto.class, modelMapper);
    }

    //get by date time is future
    public List<EventAllDto> getEventDateFuture() {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();
        if(roleMail.getAuthorities().toString().equals("[ROLE_student]")){
            LocalDateTime future = LocalDateTime.now(ZoneId.systemDefault());
            List<Event> eventAllList = eventRepository.findAllByEventStudentPastFuture(roleMail.getName(), future);
            return listMapper.mapList(eventAllList, EventAllDto.class, modelMapper);
        }

        LocalDateTime future = LocalDateTime.now(ZoneId.systemDefault());
        List<Event> eventList = eventRepository.findAllByEventFuture(future);
        return listMapper.mapList(eventList, EventAllDto.class, modelMapper);
    }

    //get by date time is selected
    public List<EventAllDto> getEventDateSelect(@PathVariable Date eventStartTime) {
        Authentication roleMail = SecurityContextHolder.getContext().getAuthentication();
        if(roleMail.getAuthorities().toString().equals("[ROLE_student]")){
            List<Event> eventList = eventRepository.findAllByEventStudentSelectDate(roleMail.getName(), eventStartTime);
            return listMapper.mapList(eventList, EventAllDto.class, modelMapper);
        }

        List<Event> eventList = eventRepository.findAllByEventSelectDate(eventStartTime);
        return listMapper.mapList(eventList, EventAllDto.class, modelMapper);
    }


    //check Overlap v2
//    public boolean checkOverLap(EventScheduleDto eventNew, List<Event> allEvent){
////        Event event = eventRepository.findById(eventNew.getEventId())
////                .orElseThrow(()->{
////                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
////                );
////        for(EventAllDto ev : getEventAll()){
//        for(EventAllDto ev : getEventAll()){
//            Instant start = eventNew.getEventStartTime();
//            Instant end = eventNew.getEventStartTime().plus(eventNew.getEventDuration(), ChronoUnit.MINUTES);
//            Instant checkStart = ev.getEventStartTime();
//            Instant checkEnd = ev.getEventStartTime().plus(ev.getEventDuration(), ChronoUnit.MINUTES);
//            interval.isOverlap(start,end,checkStart,checkEnd);
//        }
//        return true;
//    }

    //check Overlap
//    public boolean checkOverLap(EventScheduleDto eventNew, List<Event> allEvent){
////        Event event = eventRepository.findById(eventNew.getEventId())
////                .orElseThrow(()->{
////                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
////                );
////        for(EventAllDto ev : getEventAll()){
//        for(Event ev : allEvent){
//            Instant start = eventNew.getEventStartTime();
//            Instant end = eventNew.getEventStartTime().plus(eventNew.getEventDuration(), ChronoUnit.MINUTES);
//            Instant checkStart = ev.getEventStartTime();
//            Instant checkEnd = ev.getEventStartTime().plus(ev.getEventDuration(), ChronoUnit.MINUTES);
//            interval.isOverlap(start,end,checkStart,checkEnd);
//        }
//        return true;
//    }

    //post dto + check email
//    public Event create(EventScheduleDto eventNew){
//        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
//        Matcher m = p.matcher(eventNew.getBookingEmail());
//        boolean matchFound = m.matches();
//        if(matchFound && dateFuture(eventNew)){
//            checkLenghtForPost(eventNew);
//            Event event = modelMapper.map(eventNew, Event.class);
//            return eventRepository.saveAndFlush(event);
//        }
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your E-mail isn't correct");
////        return null;
//    }
//
//    public Event checkLenghtForPost(EventScheduleDto eventNew){
//        if(eventNew.getBookingName().length() > 100){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your Name is over 100 words");
//        }if(eventNew.getBookingEmail().length() > 45){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your E-mail is over 45 words");
//        }if(eventNew.getEventNotes().length() > 500){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your Note is over 500 words");
//        }
//        return null;
//    }
//
//    public Event checkLenghtForPut(EventUpdateDto eventNew){
//        if(eventNew.getEventNotes().length() > 500){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your Note is over 500 words");
//        }
//        return null;
//    }

    //check date future
//    public boolean dateFuture(EventScheduleDto eventNew){
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        LocalDateTime today = LocalDateTime.now(ZoneId.systemDefault());
//        Instant eventNewDate = eventNew.getEventStartTime();
//        String eventNewDate2 = modelMapper.map(eventNewDate,String.class);
//        LocalDateTime dateEntered = LocalDateTime.parse(eventNewDate2, dateFormatter);
////        dateEntered.plusMinutes(1);
//        if(dateEntered.isAfter(today)){
//            return true;
//        }
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is past pls check your date/time again");
//    }
//
//    public boolean dateFutureEdit(EventUpdateDto eventNew){
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        LocalDateTime today = LocalDateTime.now(ZoneId.systemDefault());
//        Instant eventNewDate = eventNew.getEventStartTime();
//        String eventNewDate2 = modelMapper.map(eventNewDate,String.class);
//        LocalDateTime dateEntered = LocalDateTime.parse(eventNewDate2, dateFormatter);
//        if(dateEntered.isAfter(today)){
//            return true;
//        }
//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is past pls check your date/time again");
//    }


}
