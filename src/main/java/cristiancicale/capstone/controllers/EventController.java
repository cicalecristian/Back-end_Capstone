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
    public Page<EventRespDTO> getEvents(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "date") String sortBy) {

        Page<Event> events = eventService.findAll(page, size, sortBy);

        return events.map(event -> new EventRespDTO(event.getId(), event.getTitle(), event.getCity(), event.getCountry(),
                event.getDate(), event.getSeat(), event.getArtist().getId()));
    }

    @GetMapping("/{id}")
    public EventRespDTO getById(@PathVariable UUID id) {
        Event found = eventService.findById(id);
        return new EventRespDTO(found.getId(), found.getTitle(), found.getCity(), found.getCountry(), found.getDate(),
                found.getSeat(), found.getArtist().getId());
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
