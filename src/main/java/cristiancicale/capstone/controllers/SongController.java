package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.Song;
import cristiancicale.capstone.payloads.SongArtistDTO;
import cristiancicale.capstone.payloads.SongDTO;
import cristiancicale.capstone.services.SongService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public Song save(@RequestBody @Validated SongDTO body) {
        return songService.save(body);
    }

    @GetMapping
    public Page<Song> getSongs(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "title") String sortBy) {
        return this.songService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Song getById(@PathVariable UUID id) {
        return songService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Song getByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated SongDTO body) {
        return songService.findByIdAndUpdate(id, body);
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
}
