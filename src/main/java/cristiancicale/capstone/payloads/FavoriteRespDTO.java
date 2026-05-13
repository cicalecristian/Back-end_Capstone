package cristiancicale.capstone.payloads;

import java.util.UUID;

public record FavoriteRespDTO(
        UUID id,

        UUID songId
) {
}
