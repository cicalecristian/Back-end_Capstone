package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.Event;
import cristiancicale.capstone.payloads.EventDTO;
import cristiancicale.capstone.payloads.EventRespDTO;
import cristiancicale.capstone.services.EventService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public EventRespDTO save(@RequestBody @Validated EventDTO body) {
        Event newEvent = this.eventService.save(body);
        return new EventRespDTO(newEvent.getId(), newEvent.getTitle(), newEvent.getCity(), newEvent.getCountry(),
                newEvent.getDate(), newEvent.getSeat(), newEvent.getArtist().getId());
    }

    @GetMapping
    public Page<Event> getEvents(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "date") String sortBy) {
        return this.eventService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Event getById(@PathVariable UUID id) {
        return eventService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EventRespDTO getByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated EventDTO body) {

        Event updatedEvent = eventService.findByIdAndUpdate(id, body);

        return new EventRespDTO(updatedEvent.getId(), updatedEvent.getTitle(), updatedEvent.getCity(),
                updatedEvent.getCountry(), updatedEvent.getDate(), updatedEvent.getSeat(), updatedEvent.getArtist().getId());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable UUID id) {
        eventService.findByIdAndDelete(id);
    }
}
