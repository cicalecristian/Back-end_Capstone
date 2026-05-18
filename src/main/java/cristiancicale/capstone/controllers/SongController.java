package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.Song;
import cristiancicale.capstone.payloads.SongArtistDTO;
import cristiancicale.capstone.payloads.SongArtistRespDTO;
import cristiancicale.capstone.payloads.SongDTO;
import cristiancicale.capstone.payloads.SongRespDTO;
import cristiancicale.capstone.services.SongService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public SongRespDTO save(@RequestBody @Validated SongDTO body) {

        Song newSong = songService.save(body);

        Set<SongArtistRespDTO> artistIds = newSong.getSongArtists().stream()
                .map(songArtist -> new SongArtistRespDTO(
                        songArtist.getId(),
                        songArtist.getArtist().getId(),
                        songArtist.getRole()))
                .collect(Collectors.toSet());

        return new SongRespDTO(newSong.getId(), newSong.getTitle(), newSong.getCover(), newSong.getDuration(), newSong.getGenre(),
                newSong.getReleaseDate(), artistIds
        );
    }

    @GetMapping
    public Page<SongRespDTO> getSongs(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "title") String sortBy) {

        Page<Song> songs = songService.findAll(page, size, sortBy);

        return songs.map(song -> {

            Set<SongArtistRespDTO> artistIds = song.getSongArtists().stream()
                    .map(songArtist -> new SongArtistRespDTO(
                            songArtist.getId(),
                            songArtist.getArtist().getId(),
                            songArtist.getRole()))
                    .collect(Collectors.toSet());

            return new SongRespDTO(song.getId(), song.getTitle(), song.getCover(), song.getDuration(), song.getGenre(),
                    song.getReleaseDate(), artistIds);
        });
    }

    @GetMapping("/{id}")
    public Song getById(@PathVariable UUID id) {
        return songService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SongRespDTO getByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated SongDTO body) {

        Song updateSong = songService.findByIdAndUpdate(id, body);

        Set<SongArtistRespDTO> artistIds =
                updateSong.getSongArtists().stream()
                        .map(songArtist -> new SongArtistRespDTO(
                                songArtist.getId(),
                                songArtist.getArtist().getId(),
                                songArtist.getRole()))
                        .collect(Collectors.toSet());

        return new SongRespDTO(updateSong.getId(), updateSong.getTitle(), updateSong.getCover(), updateSong.getDuration(),
                updateSong.getGenre(), updateSong.getReleaseDate(), artistIds
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable UUID id) {
        songService.findByIdAndDelete(id);
    }

    @PostMapping("/{songId}/artists")
    @PreAuthorize("hasRole('ADMIN')")
    public Song addArtistToSong(@PathVariable UUID songId, @RequestBody @Validated SongArtistDTO body) {
        return songService.addArtistToSong(songId, body);
    }

    @GetMapping("/artist/{artistId}")
    public List<Song> getSongsByArtist(@PathVariable UUID artistId) {
        return songService.findSongsByArtist(artistId);
    }
}