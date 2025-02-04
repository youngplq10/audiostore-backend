package dev.starzynski.audiostore.Service;

import dev.starzynski.audiostore.Entity.Audiobook;
import dev.starzynski.audiostore.Entity.Review;
import dev.starzynski.audiostore.Entity.User;
import dev.starzynski.audiostore.Repository.AudiobookRepository;
import dev.starzynski.audiostore.Repository.ReviewRepository;
import dev.starzynski.audiostore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AudiobookRepository audiobookRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public String createReview(Review review, String audiobookTitle, String username){
        Audiobook audiobook = audiobookRepository.findAudiobookByTitleIgnoreCase(audiobookTitle).orElseThrow();

        User user = userRepository.findUserByUsername(username).orElseThrow();

        review.setAudiobook(audiobook);

        review.setUser(user);

        reviewRepository.insert(review);

        audiobook.getReviews().add(review);

        audiobookRepository.save(audiobook);

        user.getReviews().add(review);

        userRepository.save(user);

        return "created";
    }

    public List<Review> getAllReviewsOfAudiobook(String title) {
        Optional<Audiobook> optionalAudiobook = audiobookRepository.findAudiobookByTitleIgnoreCase(title);

        if (optionalAudiobook.isPresent()) {
            Audiobook audiobook = optionalAudiobook.get();

            return audiobook.getReviews().reversed();
        } else {
            return new ArrayList<>();
        }
    }
}
