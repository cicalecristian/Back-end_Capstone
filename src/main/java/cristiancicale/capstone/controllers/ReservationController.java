package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.Reservation;
import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.payloads.ReservationDTO;
import cristiancicale.capstone.payloads.ReservationRespDTO;
import cristiancicale.capstone.services.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationRespDTO save(@RequestBody @Validated ReservationDTO body, @AuthenticationPrincipal User currentUser) {
        Reservation newReservation = this.reservationService.save(body, currentUser);
        return new ReservationRespDTO(newReservation.getId(), newReservation.getTickets(),
                newReservation.getUser().getId(), newReservation.getEvent().getId(), newReservation.getCreatedAt());
    }

    @GetMapping
    public Page<ReservationRespDTO> getReservations(@AuthenticationPrincipal User currentUser,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Page<Reservation> reservations = reservationService.findAll(currentUser, page, size);
        return reservations.map(reservation -> new ReservationRespDTO(reservation.getId(), reservation.getTickets(),
                reservation.getUser().getId(), reservation.getEvent().getId(), reservation.getCreatedAt()));
    }

    @GetMapping("/{id}")
    public ReservationRespDTO getById(@PathVariable UUID id, @AuthenticationPrincipal User currentUser) {
        Reservation found = reservationService.findById(id, currentUser);
        return new ReservationRespDTO(found.getId(), found.getTickets(), found.getUser().getId(),
                found.getEvent().getId(), found.getCreatedAt());
    }

    @GetMapping("/events/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ReservationRespDTO> getByEvent(@PathVariable UUID eventId) {

        List<Reservation> reservations = reservationService.findByEvent(eventId);

        return reservations.stream()
                .map(reservation -> new ReservationRespDTO(reservation.getId(), reservation.getTickets(),
                        reservation.getUser().getId(), reservation.getEvent().getId(), reservation.getCreatedAt())).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable UUID id, @AuthenticationPrincipal User currentUser) {
        reservationService.findByIdAndDelete(id, currentUser);
    }
}
