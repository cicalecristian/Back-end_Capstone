package cristiancicale.capstone.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record EventDTO(

        @NotBlank(message = "Titolo obbligatorio")
        @Size(min = 3, max = 50, message = "Il titolo deve contenere tra i 2 e i 50 caratteri")
        String title,

        @NotBlank(message = "Città obbligatoria")
        @Size(min = 2, max = 50, message = "La città deve essere tra 2 e 50 caratteri")
        String city,

        @NotBlank(message = "Paese obbligatorio")
        @Size(min = 2, max = 50, message = "Il paese deve essere tra 2 e 50 caratteri")
        String country,

        @NotNull(message = "La data è obbligatoria")
        @Future(message = "La data deve essere futura")
        LocalDate date,

        @NotBlank(message = "I posti sono obbligatori")
        @Min(value = 100, message = "I posti devono essere almeno 100")
        int seat,

        @NotNull(message = "Artist id obbligatorio")
        UUID artistId
) {
}
