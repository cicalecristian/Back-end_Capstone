package cristiancicale.capstone.payloads;

import cristiancicale.capstone.enums.Genre;

import java.time.LocalDate;

public record SeedSongDTO(
        String title,

        String cover,

        int duration,

        Genre genre,

        LocalDate releaseDate
) {
}
