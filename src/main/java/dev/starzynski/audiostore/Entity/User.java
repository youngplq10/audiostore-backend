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

    @DBRef
    @JsonIgnoreProperties("genre")
    private List<Audiobook> likedAudiobooks;

    public User(){
        this.likedAudiobooks = new ArrayList<>();
        this.id = new ObjectId();
    }

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
}
