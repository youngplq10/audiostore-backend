package dev.starzynski.audiostore.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Document(collection = "audiobook")
public class Audiobook {

    @Id
    private ObjectId id;

    private String title;

    private String description;

    private String author;

    private String coverLink;

    private String audioLink;

    @JsonIgnoreProperties("audiobooks")
    @DocumentReference(lazy = true)
    private Genre genre;

    @DocumentReference(lazy = true)
    @JsonIgnoreProperties("audiobook")
    private List<Review> reviews;

    private Integer duration;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date published_at_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date added_at_date;

    public Audiobook(){
        added_at_date = new Date();
        duration = 0;
        id = new ObjectId();
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCoverLink() { return coverLink; }
    public void setCoverLink(String cover) { this.coverLink = cover; }

    public String getAudioLink() { return audioLink; }
    public void setAudioLink(String audioLink) { this.audioLink = audioLink; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public Date getPublished_at_date() { return published_at_date; }
    public void setPublished_at_date(Date published_at_date) { this.published_at_date = published_at_date; }

    public Date getAdded_at_date() { return added_at_date; }

    public List<Review> getReviews(){ return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
}
