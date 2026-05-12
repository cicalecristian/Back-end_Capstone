package cristiancicale.capstone.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationDTO(
        @Min(value = 1, message = "Bisogna prenotare almeno un posto")
        @Max(value = 4, message = "Il massimo di posti prenotabili è 4")
        int seat,

        @NotNull(message = "Event id obbligatorio")
        UUID eventId
) {
}
