package cristiancicale.capstone.controllers;

import cristiancicale.capstone.entities.Review;
import cristiancicale.capstone.entities.User;
import cristiancicale.capstone.payloads.ReviewDTO;
import cristiancicale.capstone.payloads.ReviewRespDTO;
import cristiancicale.capstone.payloads.ReviewUpdateDTO;
import cristiancicale.capstone.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    public final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewRespDTO save(@RequestBody @Validated ReviewDTO body, @AuthenticationPrincipal User currentUser) {
        Review newReview = this.reviewService.save(body, currentUser);
        return new ReviewRespDTO(newReview.getId(), newReview.getRating());
    }

    @GetMapping
    public Page<ReviewRespDTO> getReviews(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "rating") String sortBy) {
        Page<Review> reviews = reviewService.findAll(page, size, sortBy);
        return reviews.map(review -> new ReviewRespDTO(review.getId(), review.getRating()));
    }

    @GetMapping("/song/{songId}")
    public List<ReviewRespDTO> getBySong(@PathVariable UUID songId) {

        List<Review> reviews = reviewService.findBySong(songId);
        return reviews.stream()
                .map(review -> new ReviewRespDTO(review.getId(), review.getRating())).toList();
    }

    @PatchMapping("/{songId}")
    public ReviewRespDTO getByIdAndUpdate(@PathVariable UUID songId, @RequestBody @Validated ReviewUpdateDTO body, @AuthenticationPrincipal User currentUser) {

        Review updateReview = reviewService.findByIdAndUpdate(songId, body, currentUser);
        return new ReviewRespDTO(updateReview.getId(), updateReview.getRating());
    }

    @GetMapping("/average/{songId}")
    public double getAverageRating(@PathVariable UUID songId) {
        return reviewService.getAverageRating(songId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id, @AuthenticationPrincipal User currentUser) {
        reviewService.findByIdAndDelete(id, currentUser);
    }
}
