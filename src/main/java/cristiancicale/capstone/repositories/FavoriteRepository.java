package cristiancicale.capstone.repositories;

import cristiancicale.capstone.entities.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    Page<Favorite> findByUserId(UUID userId, Pageable pageable);

    boolean existsByUserIdAndSongId(UUID userId, UUID songId);
}
