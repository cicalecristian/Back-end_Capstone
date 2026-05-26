package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.Favorite;
import cristiancicale.capstone.entities.Song;
import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.payloads.FavoriteDTO;
import cristiancicale.capstone.payloads.FavoriteRespDTO;
import cristiancicale.capstone.payloads.SongArtistRespDTO;
import cristiancicale.capstone.services.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteRespDTO save(@RequestBody @Valid FavoriteDTO body, @AuthenticationPrincipal User currentUser) {

        Favorite newFavorite = this.favoriteService.save(body, currentUser);

        Song song = newFavorite.getSong();

        Set<SongArtistRespDTO> artists = song.getSongArtists().stream()
                .map(songArtist -> new SongArtistRespDTO(songArtist.getId(), songArtist.getArtist().getId(),
                        songArtist.getRole(), songArtist.getArtist().getArtistName()))
                .collect(Collectors.toSet());

        return new FavoriteRespDTO(newFavorite.getId(), song.getId(), song.getTitle(), song.getCover(),
                song.getGenre().toString(), artists);
    }

    @GetMapping
    public Page<FavoriteRespDTO> getMyFavorites(@AuthenticationPrincipal User currentUser,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Page<Favorite> favorites = favoriteService.findUserFavorites(currentUser, page, size);
        return favorites.map(favorite -> {
            Song song = favorite.getSong();
            Set<SongArtistRespDTO> artists = song.getSongArtists().stream()
                    .map(songArtist -> new SongArtistRespDTO(songArtist.getId(), songArtist.getArtist().getId(),
                            songArtist.getRole(), songArtist.getArtist().getArtistName())
                    )
                    .collect(Collectors.toSet());

            return new FavoriteRespDTO(favorite.getId(), song.getId(), song.getTitle(), song.getCover(),
                    song.getGenre().toString(), artists);
        });
    }

    @DeleteMapping("/{favoriteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@PathVariable UUID favoriteId, @AuthenticationPrincipal User currentUser) {
        favoriteService.findByIdAndDelete(favoriteId, currentUser);
    }
}
