package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.Review;
import dev.starzynski.audiostore.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/public/reviews")
    public ResponseEntity<List<Review>> getAllReviews(){
        return new ResponseEntity<List<Review>> (reviewService.getAllReviews(), HttpStatus.OK);
    }

    @GetMapping("/public/reviews/{title}")
    public ResponseEntity<List<Review>> getAllReviewsOfAudiobook(@PathVariable String title) {
        return new ResponseEntity<List<Review>> (reviewService.getAllReviewsOfAudiobook(title), HttpStatus.OK);
    }

    @PostMapping("/auth/review")
    public ResponseEntity<String> createReview(@Validated @RequestParam String reviewBody, @Validated @RequestParam String audiobookTitle, @Validated @RequestParam Integer stars, @Validated @RequestParam String username) {
        return new ResponseEntity<String> (reviewService.createReview(reviewBody, stars, audiobookTitle, username), HttpStatus.CREATED);
    }
}
