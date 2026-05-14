package cristiancicale.capstone.repositories;

import cristiancicale.capstone.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
    Optional<Artist> findByArtistName(String artistName);
}
