package cristiancicale.capstone.payloads;

import java.time.LocalDate;
import java.util.UUID;

public record EventRespDTO(

        UUID id,

        String title,

        String city,

        String country,

        LocalDate date,

        int seat,

        UUID artistId
) {
}
