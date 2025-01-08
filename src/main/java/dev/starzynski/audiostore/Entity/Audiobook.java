package dev.starzynski.audiostore.Entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "audiobook")
public class Audiobook {

    @Id
    private ObjectId id;
    private String title;
    private String author;
    private String cover;
    private String audioLink;

}
