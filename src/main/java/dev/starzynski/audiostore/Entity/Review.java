package dev.starzynski.audiostore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;
import java.util.Optional;

@Document(collection = "review")
public class Review {

    @Id
    private ObjectId id;

    private Integer stars;

    private String reviewBody;

    @DBRef
    @JsonIgnoreProperties("review")
    private Audiobook audiobook;

    @DBRef
    @JsonIgnoreProperties("reviews")
    private User user;

    public Review() {
        this.id = new ObjectId();
    }

    public ObjectId getId() { return id; }

    public Integer getStars() { return stars; }
    public void setStars(Integer stars) { this.stars = stars; }

    public String getReviewBody() { return reviewBody; }
    public void setReviewBody(String reviewBody) { this.reviewBody = reviewBody; }

    public Audiobook getAudiobook() { return audiobook; }
    public void setAudiobook(Audiobook audiobook) { this.audiobook = audiobook; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
