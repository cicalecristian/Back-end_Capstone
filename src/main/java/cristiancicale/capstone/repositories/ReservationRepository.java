package cristiancicale.capstone.repositories;

import cristiancicale.capstone.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    Page<Reservation> findByUserId(UUID userId, Pageable pageable);

    List<Reservation> findByEventId(UUID eventId);

    boolean existsByUserIdAndEventId(UUID userId, UUID eventId);
}
