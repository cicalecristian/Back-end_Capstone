package cristiancicale.capstone.payloads;

import cristiancicale.capstone.enums.Genre;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

public record SongDTO(

        @NotBlank(message = "Titolo obbligatorio")
        String title,

        @NotBlank(message = "Cover obbligatoria")
        String cover,

        @Positive(message = "La durata deve essere positiva")
        int duration,

        @NotNull(message = "Il genere è obbligatorio")
        Genre genre,

        LocalDate releaseDate,

        @NotEmpty(message = "Inserire almeno un artista")
        @Valid
        List<SongArtistDTO> artists
) {
}
