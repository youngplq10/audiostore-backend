package dev.starzynski.audiostore.Repository;

import dev.starzynski.audiostore.Entity.Audiobook;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudiobookRepository extends MongoRepository<Audiobook, ObjectId> {
    Optional<Audiobook> findAudiobookByTitleIgnoreCase(String title);

    Boolean existsAudiobookByTitleIgnoreCase(String title);

    void deleteAudiobookByTitleIgnoreCase(String title);
}
