package cristiancicale.capstone.payloads;

import cristiancicale.capstone.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ArtistDTO(

        @NotBlank(message = "Artist name is required")
        @Size(min = 2, max = 50, message = "Artist name must be between 2 and 50 characters")
        String artistName,

        @NotBlank(message = "Nationality is required")
        @Size(min = 2, max = 30, message = "Nationality must be between 2 and 30 characters")
        String nationality,

        @Past(message = "Invalid birth date")
        LocalDate dateOfBirth,

        @NotNull(message = "Genre is required")
        Genre genre,

        String avatar
) {
}