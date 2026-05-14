package cristiancicale.capstone.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserDTO(

        @NotBlank(message = "Username obbligatorio")
        @Size(min = 3, max = 30, message = "Username deve contenere tra i 3 e i 30 caratteri")
        String username,

        @Email(message = "Email non valida")
        @NotBlank(message = "Email obbligatoria")
        String email,

        @NotBlank(message = "Password obbligatoria")
        @Size(min = 8, message = "Minimo 8 caratteri")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
                message = "La password deve contenere almeno una maiuscola, una minuscola e un numero"
        )
        String password,

        @NotBlank(message = "Nome obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome deve essere tra 2 e 30 caratteri")
        String name,

        @NotBlank(message = "Nome obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome deve essere tra 2 e 30 caratteri")
        String surname,

        @Past(message = "Data di nascita non valida")
        @NotNull(message = "Data di nascita obbligatoria")
        LocalDate dateOfBirth,

        String avatar
) {
}
