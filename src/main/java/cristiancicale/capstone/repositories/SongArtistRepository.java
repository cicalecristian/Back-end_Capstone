package cristiancicale.capstone.repositories;

import cristiancicale.capstone.entities.SongArtist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SongArtistRepository extends JpaRepository<SongArtist, UUID> {
}
