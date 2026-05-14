package cristiancicale.capstone.repositories;

import cristiancicale.capstone.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface SongRepository extends JpaRepository<Song, UUID> {
    boolean existsByTitleAndReleaseDate(String title, LocalDate releaseDate);
}
