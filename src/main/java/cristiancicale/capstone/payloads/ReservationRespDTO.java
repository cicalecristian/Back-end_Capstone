package cristiancicale.capstone.payloads;

import java.util.UUID;

public record ReservationRespDTO(
        UUID id,

        int seat,

        UUID userId,

        UUID eventId
) {
}
