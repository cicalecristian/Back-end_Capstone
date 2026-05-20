package cristiancicale.capstone.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record EventDTO(

        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 50, message = "The title must contain between 3 and 50 characters")
        String title,

        @NotBlank(message = "City is required")
        @Size(min = 2, max = 50, message = "The city must be between 2 and 50 characters")
        String city,

        @NotBlank(message = "Country is required")
        @Size(min = 2, max = 50, message = "The country must be between 2 and 50 characters")
        String country,

        @NotNull(message = "Date is required")
        @Future(message = "The date must be in the future")
        LocalDate date,

        @NotNull(message = "Seats are required")
        @Min(value = 100, message = "Seats must be at least 100")
        @Max(value = 200000, message = "Seats cannot exceed 200000")
        Integer seat,

        @NotNull(message = "Artist id is required")
        UUID artistId
) {
}