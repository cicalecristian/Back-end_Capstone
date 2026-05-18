package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.Artist;
import cristiancicale.capstone.payloads.ArtistDTO;
import cristiancicale.capstone.payloads.ArtistRespDTO;
import cristiancicale.capstone.services.ArtistService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRespDTO save(@RequestBody @Validated ArtistDTO body) {

        Artist newArtist = this.artistService.save(body);
        return new ArtistRespDTO(newArtist.getId(), newArtist.getArtistName(), newArtist.getNationality(),
                newArtist.getDateOfBirth(), newArtist.getGenre(), newArtist.getAvatar());
    }

    @GetMapping
    public Page<ArtistRespDTO> getArtists(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "artistName") String sortBy) {

        Page<Artist> artists = artistService.findAll(page, size, sortBy);

        return artists.map(artist -> new ArtistRespDTO(artist.getId(), artist.getArtistName(), artist.getNationality(),
                artist.getDateOfBirth(), artist.getGenre(), artist.getAvatar())
        );
    }

    @GetMapping("/{id}")
    public ArtistRespDTO getById(@PathVariable UUID id) {
        Artist found = artistService.findById(id);
        return new ArtistRespDTO(found.getId(), found.getArtistName(), found.getNationality(), found.getDateOfBirth(),
                found.getGenre(), found.getAvatar());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ArtistRespDTO getByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated ArtistDTO body) {

        Artist updateArtist = artistService.findByIdAndUpdate(id, body);

        return new ArtistRespDTO(updateArtist.getId(), updateArtist.getArtistName(), updateArtist.getNationality(),
                updateArtist.getDateOfBirth(), updateArtist.getGenre(), updateArtist.getAvatar());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable UUID id) {
        artistService.findByIdAndDelete(id);
    }
}
