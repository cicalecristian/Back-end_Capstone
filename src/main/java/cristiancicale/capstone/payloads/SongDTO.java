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

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Cover is required")
        String cover,

        @Positive(message = "Duration must be positive")
        int duration,

        @NotNull(message = "Genre is required")
        Genre genre,

        LocalDate releaseDate,

        @NotEmpty(message = "At least one artist must be provided")
        @Valid
        List<SongArtistDTO> artists
) {
}