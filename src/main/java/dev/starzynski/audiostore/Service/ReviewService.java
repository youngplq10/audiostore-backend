package dev.starzynski.audiostore.Service;

import dev.starzynski.audiostore.Entity.Audiobook;
import dev.starzynski.audiostore.Entity.Review;
import dev.starzynski.audiostore.Repository.AudiobookRepository;
import dev.starzynski.audiostore.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AudiobookRepository audiobookRepository;

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public String createReview(Review review, String audiobookTitle){
        Optional<Audiobook> audiobookOptional = audiobookRepository.findAudiobookByTitleIgnoreCase(audiobookTitle);

        if (audiobookOptional.isPresent()){
            Audiobook audiobook = audiobookOptional.get();

            review.setAudiobook(audiobook);

            reviewRepository.insert(review);

            audiobook.getReviews().add(review);

            audiobookRepository.save(audiobook);

            return "created";
        }

        return "failed";
    }
}
