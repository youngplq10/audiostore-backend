package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.Review;
import dev.starzynski.audiostore.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews(){
        return new ResponseEntity<List<Review>> (reviewService.getAllReviews(), HttpStatus.OK);
    }

    @PostMapping("/review")
    public String createReview(@Validated @RequestParam String reviewBody, @Validated @RequestParam String audiobookTitle){
        Review newReview = new Review();
        newReview.setReviewBody(reviewBody);

        return reviewService.createReview(newReview, audiobookTitle);
    }
}
