package dev.starzynski.audiostore.Entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "genre")
public class Genre {
    @Id
    private ObjectId id;

    private String name;

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
}
