package sit.int221.mydb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sit.int221.mydb.dto.EventAllDto;
import sit.int221.mydb.dto.EventDetailDto;
import sit.int221.mydb.dto.EventScheduleDto;
import sit.int221.mydb.dto.EventUpdateDto;
import sit.int221.mydb.entities.Event;
import sit.int221.mydb.repositories.EventRepository;
import sit.int221.mydb.services.EventService;
import sit.int221.mydb.utils.Interval;

import javax.validation.Valid;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('admin','student','lecturer')")
    public List<EventAllDto> getEventAll() {
        return eventService.getEventAll();
    }

    @GetMapping("{eventId}")
    @PreAuthorize("hasAnyRole('admin','student','lecturer')")
    public EventDetailDto getEventDetail(@PathVariable Integer eventId){
        return eventService.getDetail(eventId);
    }

    @GetMapping("eventCategoryId={eventCategory_eventCategoryId}")
    @PreAuthorize("hasAnyRole('admin','student')")
    public List<EventAllDto> getEventListByCategoryId(@PathVariable Integer eventCategory_eventCategoryId) {
        return eventService.getEventListByCategoryId(eventCategory_eventCategoryId);
    }

    @GetMapping("past")
    @PreAuthorize("hasAnyRole('admin','student')")
    public List<EventAllDto> getEventPast() {
        return eventService.getEventDatePast();
    }

    @GetMapping("future")
    @PreAuthorize("hasAnyRole('admin','student')")
    public List<EventAllDto> getEventFuture() {
        return eventService.getEventDateFuture();
    }

    @GetMapping("date/{eventStartTime}")
    @PreAuthorize("hasAnyRole('admin','student')")
    public List<EventAllDto> getEventSelectDate(@PathVariable Date eventStartTime) {
        return eventService.getEventDateSelect(eventStartTime);
    }

    //post แบบ dto + check email + check date future (validate Spring boot)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("!hasRole('lecturer')")
    public Event create(@Valid @ModelAttribute EventScheduleDto newEvent) {
        return eventService.create(newEvent);
    }
//    @PostMapping("")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void create(@Valid @RequestBody EventScheduleDto newEvent,List<Event> allEvent) {
//        eventService.create(newEvent, allEvent);
//    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAnyRole('admin','student')")
    public void delete(@PathVariable Integer eventId) {
        eventService.deleteEventId(eventId);
    }

    //put แบบ dto ver.3
    @PutMapping("/{eventId}")
    @PreAuthorize("hasAnyRole('admin','student')")
    public Event edit(@Valid @ModelAttribute EventUpdateDto editEvent, @PathVariable Integer eventId) {
        return eventService.editEvent(editEvent,eventId);
    }

}
