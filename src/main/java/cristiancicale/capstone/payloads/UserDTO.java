package cristiancicale.capstone.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserDTO(

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 30, message = "Username must contain between 3 and 30 characters")
        String username,

        @Email(message = "Invalid email")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Minimum 8 characters")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, and one number"
        )
        String password,

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
        String name,

        @NotBlank(message = "Surname is required")
        @Size(min = 2, max = 30, message = "Surname must be between 2 and 30 characters")
        String surname,

        @Past(message = "Invalid birth date")
        @NotNull(message = "Birth date is required")
        LocalDate dateOfBirth,

        String avatar
) {
}