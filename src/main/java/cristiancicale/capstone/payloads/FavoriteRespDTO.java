package cristiancicale.capstone.payloads;

import java.util.Set;
import java.util.UUID;

public record FavoriteRespDTO(
        UUID id,

        UUID songId,

        String title,

        String cover,

        String genre,

        Set<SongArtistRespDTO> artists
) {
}
