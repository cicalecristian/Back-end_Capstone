package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.Favorite;
import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.payloads.FavoriteDTO;
import cristiancicale.capstone.payloads.FavoriteRespDTO;
import cristiancicale.capstone.services.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
        return new FavoriteRespDTO(newFavorite.getId(), newFavorite.getSong().getId());
    }

    @GetMapping
    public Page<FavoriteRespDTO> getMyFavorites(@AuthenticationPrincipal User currentUser,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        Page<Favorite> favorites = favoriteService.findUserFavorites(currentUser, page, size);
        return favorites.map(favorite -> new FavoriteRespDTO(favorite.getId(), favorite.getSong().getId()));
    }

    @DeleteMapping("/{favoriteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@PathVariable UUID favoriteId, @AuthenticationPrincipal User currentUser) {
        favoriteService.findByIdAndDelete(favoriteId, currentUser);
    }
}
