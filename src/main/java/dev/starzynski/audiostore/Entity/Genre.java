package dev.starzynski.audiostore.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Document(collection = "genre")
public class Genre {
    @Id
    private ObjectId id;

    private String name;

    @JsonIgnoreProperties("genre")
    @DocumentReference(lazy = true)
    private List<Audiobook> audiobooks;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date added_at_date;

    public Genre(){
        added_at_date = new Date();
        id = new ObjectId();
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public ObjectId getId(){
        return id;
    }

    public List<Audiobook> getAudiobooks() { return audiobooks; }
}
