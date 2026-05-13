package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.Artist;
import cristiancicale.capstone.payloads.ArtistDTO;
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
    public Artist save(@RequestBody @Validated ArtistDTO body) {
        return artistService.save(body);
    }

    @GetMapping
    public Page<Artist> getArtists(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "artistName") String sortBy) {
        return this.artistService.findAll(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Artist getById(@PathVariable UUID id) {
        return artistService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Artist getByIdAndUpdate(@PathVariable UUID id, @RequestBody @Validated ArtistDTO body) {
        return artistService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable UUID id) {
        artistService.findByIdAndDelete(id);
    }
}
