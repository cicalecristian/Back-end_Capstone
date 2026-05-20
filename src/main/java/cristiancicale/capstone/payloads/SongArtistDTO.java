package cristiancicale.capstone.payloads;

import cristiancicale.capstone.enums.RoleArtist;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SongArtistDTO(

        @NotNull(message = "Artist id is required")
        UUID artistId,

        @NotNull(message = "Role is required")
        RoleArtist role
) {
}