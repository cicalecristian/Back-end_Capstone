package cristiancicale.capstone.payloads;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationRespDTO(
        UUID id,

        int tickets,

        UUID userId,

        UUID eventId,

        LocalDateTime createdAt
) {
}
