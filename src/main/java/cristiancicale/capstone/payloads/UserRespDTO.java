package cristiancicale.capstone.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import cristiancicale.capstone.enums.RoleUser;

import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserRespDTO(
        UUID id,

        String username,

        String email,

        String name,

        String surname,

        LocalDate dateOfBirth,

        RoleUser role,

        String avatar
) {
}
