package sit.int221.mydb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sit.int221.mydb.dto.EventDetailDto;
import sit.int221.mydb.dto.EventScheduleDto;
import sit.int221.mydb.entities.Event;
import sit.int221.mydb.entities.Eventcategory;
import sit.int221.mydb.repositories.EventcategoryRepository;
import sit.int221.mydb.services.EventCategoryService;
import sit.int221.mydb.dto.EventCategoryDto;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/eventcategories")
public class EventCategoryController {

    @Autowired
    private EventCategoryService eventCategoryService;

    @GetMapping("")
    public List<EventCategoryDto> get(){
        return eventCategoryService.getEventAll();
    }

    @GetMapping("{eventCategoryId}")
    public Eventcategory getCategoryDetail(@PathVariable Integer eventCategoryId){
        return eventCategoryService.getDetail(eventCategoryId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Eventcategory create(@Valid @RequestBody EventCategoryDto eventCatNew) {
        return eventCategoryService.create(eventCatNew);
    }

    @PutMapping("{eventCategoryId}")
    public Eventcategory edit(@Valid @RequestBody EventCategoryDto editEvent, @PathVariable Integer eventCategoryId) {
        return eventCategoryService.editEventCategory(editEvent,eventCategoryId);
    }
}
