package cristiancicale.capstone.payloads;

import cristiancicale.capstone.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ArtistDTO(

        @NotBlank(message = "Nome artista obbligatorio")
        @Size(min = 2, max = 50,
                message = "Il nome artista deve essere tra 2 e 50 caratteri")
        String artistName,

        @NotBlank(message = "Nazionalità obbligatoria")
        @Size(min = 2, max = 30,
                message = "La nazionalità deve essere tra 2 e 30 caratteri")
        String nationality,

        @Past(message = "Data di nascita non valida")
        LocalDate dateOfBirth,

        @NotNull(message = "Genere obbligatorio")
        Genre genre,

        String avatar
) {
}
