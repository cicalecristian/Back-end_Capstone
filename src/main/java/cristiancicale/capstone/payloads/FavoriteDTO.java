package cristiancicale.capstone.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FavoriteDTO(

        @NotNull(message = "Song id obbligatorio")
        UUID songId
) {
}
