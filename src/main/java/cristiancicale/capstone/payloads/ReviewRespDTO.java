package cristiancicale.capstone.payloads;

import java.util.UUID;

public record ReviewRespDTO(
        UUID id,

        int rating,

        UUID userId,

        UUID songId
) {
}
