package cristiancicale.capstone.services;

import cristiancicale.capstone.entities.Artist;
import cristiancicale.capstone.entities.Event;
import cristiancicale.capstone.exceptions.NotFoundException;
import cristiancicale.capstone.payloads.EventDTO;
import cristiancicale.capstone.repositories.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class EventService {

    public final EventRepository eventRepository;
    public final ArtistService artistService;

    public EventService(EventRepository eventRepository, ArtistService artistService) {
        this.eventRepository = eventRepository;
        this.artistService = artistService;
    }

    public Event save(EventDTO body) {

        Artist artist = artistService.findById(body.artistId());
        String cover = body.cover();

        if (cover == null || cover.isBlank()) {
            cover = "https://img.magnific.com/free-vector/party-audience_1048-7433.jpg?semt=ais_hybrid&w=740&q=80";
        }

        Event event = new Event(body.title(), body.city(), body.country(), body.date(), body.seat(), artist, cover);

        return eventRepository.save(event);
    }

    public Page<Event> findAll(int page, int size, String sortBy) {
        if (size <= 0) size = 10;
        if (size > 1000) size = 1000;
        if (page < 0) page = 0;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventRepository.findAll(pageable);
    }

    public Event findById(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Event findByIdAndUpdate(UUID id, EventDTO body) {

        Event found = findById(id);

        Artist artist = artistService.findById(body.artistId());

        found.setTitle(body.title());
        found.setCity(body.city());
        found.setCountry(body.country());
        found.setDate(body.date());
        found.setSeat(body.seat());
        found.setArtist(artist);
        found.setCover(body.cover());

        return eventRepository.save(found);
    }

    public void findByIdAndDelete(UUID id) {
        Event found = findById(id);
        eventRepository.delete(found);
    }
}
