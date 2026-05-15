package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.Favorite;
import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.payloads.FavoriteDTO;
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
    public Favorite save(@RequestBody @Valid FavoriteDTO body, @AuthenticationPrincipal User currentUser) {
        return favoriteService.save(body, currentUser);
    }

    @GetMapping
    public Page<Favorite> getMyFavorites(@AuthenticationPrincipal User currentUser,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return favoriteService.findUserFavorite(currentUser, page, size);
    }

    @DeleteMapping("/{favoriteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@PathVariable UUID favoriteId, @AuthenticationPrincipal User currentUser) {
        favoriteService.findByIdAndDelete(favoriteId, currentUser);
    }
}
