package cristiancicale.capstone.payloads;

import cristiancicale.capstone.enums.RoleArtist;

import java.util.UUID;

public record SongArtistRespDTO(
        UUID id,

        UUID songId,

        UUID artistId,

        RoleArtist role
) {
}
