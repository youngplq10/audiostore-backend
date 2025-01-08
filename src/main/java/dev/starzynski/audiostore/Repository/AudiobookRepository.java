package dev.starzynski.audiostore.Repository;

import dev.starzynski.audiostore.Entity.Audiobook;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudiobookRepository extends MongoRepository<Audiobook, ObjectId> {
}
