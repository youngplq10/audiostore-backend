package dev.starzynski.audiostore.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
public class User {

    @Id
    private ObjectId id;

    private String username;

    private String email;

    private String password;

    private Integer phone;

    private String role;

    @DBRef
    @JsonIgnoreProperties("genre")
    private List<Audiobook> likedAudiobooks;

    @DBRef
    @JsonIgnoreProperties("user")
    private List<Review> reviews;

    public User(){
        this.likedAudiobooks = new ArrayList<>();
        this.id = new ObjectId();
        this.reviews = new ArrayList<>();
    }

    public ObjectId getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getPhone() { return phone; }
    public void setPhone(Integer phone) { this.phone = phone; }

    public List<Audiobook> getLikedAudiobooks() { return likedAudiobooks; }
    public void setLikedAudiobooks(List<Audiobook> likedAudiobooks) { this.likedAudiobooks = likedAudiobooks; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
}
