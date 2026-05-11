package cristiancicale.capstone.payloads;

import cristiancicale.capstone.enums.RoleArtist;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SongArtistDTO(

        @NotNull(message = "Artist id obbligatorio")
        UUID artistId,

        @NotNull(message = "Ruolo obbligatorio")
        RoleArtist role
) {
}
