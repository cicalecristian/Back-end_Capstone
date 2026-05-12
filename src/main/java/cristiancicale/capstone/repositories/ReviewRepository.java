package cristiancicale.capstone.repositories;

import cristiancicale.capstone.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findBySongId(UUID songId);

    List<Review> findByUserId(UUID userId);

    boolean existsByUserIdAndSongId(UUID userId, UUID songId);

    Optional<Review> findByUserIdAndSongId(UUID userId, UUID songId);
}
