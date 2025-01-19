package dev.starzynski.audiostore.Entity;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
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

    private List<Save> saves;

    private Integer listenTime;

    public User(){
        id = new ObjectId();
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getPhone() { return phone; }
    public void setPhone(Integer phone) { this.phone = phone; }

    public Integer getListenTime() { return listenTime; }
    public void setListenTime(Integer listenTime) { this.listenTime = listenTime; }

    public List<Save> getSaves() { return saves; }
    public void setSaves(List<Save> saves) { this.saves = saves; }
}
