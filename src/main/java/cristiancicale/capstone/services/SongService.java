package cristiancicale.capstone.services;

import cristiancicale.capstone.entities.Artist;
import cristiancicale.capstone.entities.Song;
import cristiancicale.capstone.entities.SongArtist;
import cristiancicale.capstone.exceptions.NotFoundException;
import cristiancicale.capstone.payloads.SongArtistDTO;
import cristiancicale.capstone.payloads.SongDTO;
import cristiancicale.capstone.repositories.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class SongService {

    private final SongRepository songRepository;
    private final ArtistService artistService;

    public SongService(SongRepository songRepository, ArtistService artistService) {
        this.songRepository = songRepository;
        this.artistService = artistService;
    }

    public Song save(SongDTO body) {

        Song song = new Song(body.title(), body.cover(), body.duration(), body.genre(), body.releaseDate());

        return songRepository.save(song);
    }

    public Page<Song> findAll(int page, int size, String sortBy) {
        if (size > 10 || size < 0) size = 10;
        if (page < 10) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.songRepository.findAll(pageable);
    }

    public Song findById(UUID id) {
        return songRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Song findByIdAndUpdate(UUID id, SongDTO body) {

        Song found = findById(id);

        found.setTitle(body.title());
        found.setCover(body.cover());
        found.setDuration(body.duration());
        found.setGenre(body.genre());
        found.setReleaseDate(body.releaseDate());

        found.getSongArtists().clear();

        Set<SongArtist> artists = new HashSet<>();

        for (SongArtistDTO artistDTO : body.artists()) {

            Artist artist = artistService.findById(artistDTO.artistId());

            SongArtist songArtist = new SongArtist(
                    artistDTO.role(),
                    found,
                    artist
            );

            artists.add(songArtist);
        }

        found.setSongArtists(artists);

        return songRepository.save(found);
    }

    public void findByIdAndDelete(UUID id) {
        Song found = findById(id);
        songRepository.delete(found);
    }
}
