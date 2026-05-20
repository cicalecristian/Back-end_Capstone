package cristiancicale.capstone.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReviewDTO(
        @Min(value = 1, message = "The minimum rating is 1")
        @Max(value = 5, message = "The maximum rating is 5")
        int rating,

        @NotNull(message = "Song id is required")
        UUID songId
) {
}