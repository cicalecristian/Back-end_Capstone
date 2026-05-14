package cristiancicale.capstone.payloads;

import cristiancicale.capstone.enums.Genre;

import java.time.LocalDate;
import java.util.List;

public record SeedArtistDTO(

        String artistName,

        String nationality,

        LocalDate dateOfBirth,

        Genre genre,

        String avatar,

        List<SeedSongDTO> songs
) {
}
