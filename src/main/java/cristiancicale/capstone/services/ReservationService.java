package cristiancicale.capstone.services;

import cristiancicale.capstone.entities.Event;
import cristiancicale.capstone.entities.Reservation;
import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.exceptions.AccessDeniedException;
import cristiancicale.capstone.exceptions.BadRequestException;
import cristiancicale.capstone.exceptions.NotFoundException;
import cristiancicale.capstone.payloads.ReservationDTO;
import cristiancicale.capstone.repositories.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EventService eventService;

    public ReservationService(ReservationRepository reservationRepository, EventService eventService) {
        this.reservationRepository = reservationRepository;
        this.eventService = eventService;
    }

    public Reservation save(ReservationDTO body, User user) {

        Event event = eventService.findById(body.eventId());

        if (reservationRepository.existsByUserIdAndEventId(user.getId(), event.getId())) {
            throw new BadRequestException("Hai già prenotato questo evento");
        }

        if (body.tickets() > event.getSeat()) {
            throw new BadRequestException("Posti insufficienti");
        }

        event.setSeat(event.getSeat() - body.tickets());

        Reservation reservation = new Reservation(user, event, body.tickets());

        return reservationRepository.save(reservation);
    }

    public Page<Reservation> findAll(int page, int size) {
        if (size > 10 || size < 0) size = 10;
        if (page < 10) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return this.reservationRepository.findAll(pageable);
    }

    public Reservation findById(UUID id, User currentUser) {

        Reservation found = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        boolean isOwner = found.getUser().getId().equals(currentUser.getId());

        boolean isAdmin = currentUser.getRole().name().equals("ROLE_ADMIN");

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("Non puoi accedere a questa prenotazione");
        }

        return found;
    }

    public void findByIdAndDelete(UUID id, User currentUser) {

        Reservation found = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        boolean isOwner = found.getUser().getId().equals(currentUser.getId());

        boolean isAdmin = currentUser.getRole().name().equals("ROLE_ADMIN");

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("Non puoi eliminare questa prenotazione");
        }

        reservationRepository.delete(found);
    }
}
