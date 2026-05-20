package cristiancicale.capstone.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationDTO(
        @Min(value = 1, message = "At least one seat must be reserved")
        @Max(value = 4, message = "The maximum number of reservable seats is 4")
        int tickets,

        @NotNull(message = "Event id is required")
        UUID eventId
) {
}