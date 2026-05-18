package cristiancicale.capstone.repositories;

import cristiancicale.capstone.entities.SongArtist;
import cristiancicale.capstone.enums.RoleArtist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SongArtistRepository extends JpaRepository<SongArtist, UUID> {
    boolean existsBySongIdAndArtistIdAndRole(UUID songId, UUID artistId, RoleArtist role);
}
