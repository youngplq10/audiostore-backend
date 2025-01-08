package dev.starzynski.audiostore.Entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "audiobook")
public class Audiobook {

    @Id
    private ObjectId id;
    @Setter @Getter
    private String title;
    @Setter @Getter
    private String author;
    @Setter @Getter
    private String cover;
    @Setter @Getter
    private String audioLink;
}
