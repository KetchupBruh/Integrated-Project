package sit.int221.mydb.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.mydb.dto.EventAllDto;
import sit.int221.mydb.dto.EventCategoryDto;
import sit.int221.mydb.dto.EventDetailDto;
import sit.int221.mydb.dto.EventScheduleDto;
import sit.int221.mydb.entities.Event;
import sit.int221.mydb.entities.Eventcategory;
import sit.int221.mydb.repositories.EventcategoryRepository;
import sit.int221.mydb.utils.Interval;
import sit.int221.mydb.utils.ListMapper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EventCategoryService {

    private Interval interval;

    @Autowired
    private EventcategoryRepository eventCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    public List<EventCategoryDto> getEventAll() {
        List<Eventcategory> eventCategoryAllList = eventCategoryRepository.findAll(Sort.by("id").descending());
        return listMapper.mapList(eventCategoryAllList, EventCategoryDto.class, modelMapper);
    }

    public Eventcategory create(EventCategoryDto eventCatNew){
        Eventcategory eventCategory = modelMapper.map(eventCatNew, Eventcategory.class);
        return eventCategoryRepository.saveAndFlush(eventCategory);
    }

    public Eventcategory getDetail(Integer id) {
        Eventcategory eventCategory = eventCategoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does't exist !!"));
        return modelMapper.map(eventCategory, Eventcategory.class);
    }

    public Eventcategory editEventCategory(EventCategoryDto editEventCategory, Integer eventCategoryId) {
        if(eventCategoryRepository.findCheckUniqueName(editEventCategory.getId(),editEventCategory.getEventCategoryName()) < 1){
            Eventcategory eventCategory = eventCategoryRepository.findById(eventCategoryId).orElseThrow(()->{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            });
            editEventCategory.setEventCategoryName(editEventCategory.getEventCategoryName().trim());
            modelMapper.map(editEventCategory,eventCategory);
            Eventcategory e = modelMapper.map(eventCategory,Eventcategory.class);
            return eventCategoryRepository.saveAndFlush(e);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "That Unique name");
    }

}
