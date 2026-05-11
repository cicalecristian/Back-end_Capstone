package cristiancicale.capstone.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import cristiancicale.capstone.enums.Genre;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SongRespDTO(
        UUID id,

        String title,

        String cover,

        int duration,

        Genre genre,

        LocalDate releaseDate,

        Set<UUID> artistIds
) {
}
