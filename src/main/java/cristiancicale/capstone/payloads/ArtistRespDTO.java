package cristiancicale.capstone.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import cristiancicale.capstone.enums.Genre;

import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ArtistRespDTO(
        UUID id,

        String artistName,

        String nationality,

        LocalDate dateOfBirth,

        Genre genre,

        String avatar
) {
}
