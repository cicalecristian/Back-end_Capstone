package cristiancicale.capstone.payloads;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReviewUpdateDTO(
        @Min(value = 1, message = "Il voto minimo è 1")
        @Max(value = 5, message = "Il voto massimo è 5")
        int rating
) {
}
