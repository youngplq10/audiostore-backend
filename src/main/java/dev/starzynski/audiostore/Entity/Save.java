package dev.starzynski.audiostore.Entity;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Document(collection = "save")
public class Save {

    @Id
    private ObjectId id;
    private String username;
    private String token;
    private String title;

    public ObjectId getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTitle() { return title; }
    public void setTitle(String token) { this.title = title; }
}
