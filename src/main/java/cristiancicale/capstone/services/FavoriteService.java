package cristiancicale.capstone.services;

import cristiancicale.capstone.entities.Favorite;
import cristiancicale.capstone.entities.Song;
import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.exceptions.BadRequestException;
import cristiancicale.capstone.exceptions.NotFoundException;
import cristiancicale.capstone.payloads.FavoriteDTO;
import cristiancicale.capstone.repositories.FavoriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final SongService songService;

    public FavoriteService(FavoriteRepository favoriteRepository, SongService songService) {
        this.favoriteRepository = favoriteRepository;
        this.songService = songService;
    }

    public Favorite save(FavoriteDTO body, User user) {

        Song song = songService.findById(body.songId());

        if (favoriteRepository.existsByUserIdAndSongId(user.getId(), song.getId())) {
            throw new BadRequestException("Song già nei preferiti");
        }

        Favorite favorite = new Favorite(user, song);

        return favoriteRepository.save(favorite);
    }

    public Page<Favorite> findUserFavorite(User user, int page, int size) {
        if (size > 10 || size < 0) size = 10;
        if (page < 10) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return this.favoriteRepository.findByUserId(user.getId(), pageable);
    }

    public Favorite findById(UUID id) {
        return favoriteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID songId, User user) {
        favoriteRepository.deleteByUserIdAndSongId(user.getId(), songId);
    }
}
