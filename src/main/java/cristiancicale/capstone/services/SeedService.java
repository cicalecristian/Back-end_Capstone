package cristiancicale.capstone.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cristiancicale.capstone.entities.Artist;
import cristiancicale.capstone.entities.Song;
import cristiancicale.capstone.entities.SongArtist;
import cristiancicale.capstone.enums.RoleArtist;
import cristiancicale.capstone.payloads.SeedArtistDTO;
import cristiancicale.capstone.payloads.SeedSongDTO;
import cristiancicale.capstone.repositories.ArtistRepository;
import cristiancicale.capstone.repositories.SongArtistRepository;
import cristiancicale.capstone.repositories.SongRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class SeedService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final SongArtistRepository songArtistRepository;

    public SeedService(
            ArtistRepository artistRepository,
            SongRepository songRepository,
            SongArtistRepository songArtistRepository
    ) {

        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.songArtistRepository = songArtistRepository;
    }

    public void seedDatabase() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {

            InputStream inputStream = getClass().getResourceAsStream("/json/artists.json");

            List<SeedArtistDTO> artists = mapper.readValue(
                    inputStream,
                    new TypeReference<>() {
                    }
            );

            for (SeedArtistDTO artistDTO : artists) {

                Artist artist;

                Optional<Artist> foundArtist =
                        artistRepository.findByArtistName(
                                artistDTO.artistName()
                        );

                if (foundArtist.isPresent()) {

                    artist = foundArtist.get();

                } else {

                    Artist newArtist = new Artist(
                            artistDTO.artistName(),
                            artistDTO.nationality(),
                            artistDTO.dateOfBirth(),
                            artistDTO.genre(),
                            artistDTO.avatar()
                    );

                    artist = artistRepository.save(newArtist);
                }

                for (SeedSongDTO songDTO : artistDTO.songs()) {

                    if (songRepository
                            .existsByTitleAndReleaseDate(
                                    songDTO.title(),
                                    songDTO.releaseDate()
                            )) {

                        continue;
                    }

                    Song song = new Song(
                            songDTO.title(),
                            songDTO.cover(),
                            songDTO.duration(),
                            songDTO.genre(),
                            songDTO.releaseDate()
                    );

                    Song savedSong =
                            songRepository.save(song);

                    SongArtist songArtist =
                            new SongArtist(
                                    RoleArtist.MAIN,
                                    savedSong,
                                    artist
                            );

                    songArtistRepository.save(songArtist);
                }
            }

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}
