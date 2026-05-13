package cristiancicale.capstone.services;

import cristiancicale.capstone.entities.Review;
import cristiancicale.capstone.entities.Song;
import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.exceptions.AccessDeniedException;
import cristiancicale.capstone.exceptions.BadRequestException;
import cristiancicale.capstone.exceptions.NotFoundException;
import cristiancicale.capstone.payloads.ReviewDTO;
import cristiancicale.capstone.repositories.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final SongService songService;

    public ReviewService(ReviewRepository reviewRepository, SongService songService) {
        this.reviewRepository = reviewRepository;
        this.songService = songService;
    }

    public Review save(ReviewDTO body, User user) {

        Song song = songService.findById(body.songId());

        if (reviewRepository.existsByUserIdAndSongId(user.getId(), song.getId())) {
            throw new BadRequestException("Hai già recensito questa song");
        }

        Review review = new Review(body.rating(), user, song);

        return reviewRepository.save(review);
    }

    public Page<Review> findAll(int page, int size, String sortBy) {
        if (size > 10 || size < 0) size = 10;
        if (page < 10) page = 0;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.reviewRepository.findAll(pageable);
    }

    public Review findById(UUID id) {
        return reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Recensione non trovata"));
    }

    public List<Review> findBySong(UUID songId) {

        songService.findById(songId);

        return reviewRepository.findBySongId(songId);
    }

    public Review findByIdAndUpdate(UUID songId, ReviewDTO body, User user) {

        Review found = reviewRepository.findByUserIdAndSongId(user.getId(), songId)
                .orElseThrow(() -> new NotFoundException("Recensione non trovata"));

        found.setRating(body.rating());

        return reviewRepository.save(found);
    }

    public void findByIdAndDelete(UUID id, User currentUser) {

        Review found = findById(id);

        boolean isOwner = found.getUser().getId().equals(currentUser.getId());

        boolean isAdmin = currentUser.getRole().name().equals("ROLE_ADMIN");

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("Non puoi eliminare questa recensione");
        }
        reviewRepository.delete(found);
    }

    public double getAverageRating(UUID songId) {

        List<Review> reviews = reviewRepository.findBySongId(songId);

        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }
}
