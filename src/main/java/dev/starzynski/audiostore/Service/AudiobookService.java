package dev.starzynski.audiostore.Service;

import dev.starzynski.audiostore.Entity.Audiobook;
import dev.starzynski.audiostore.Repository.AudiobookRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AudiobookService {
    @Autowired
    private AudiobookRepository audiobookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Audiobook> getAllAudiobooks() {
        return audiobookRepository.findAll();
    }

    public Optional<Audiobook> getAudiobookByTitle(String title) {
        return audiobookRepository.findAudiobookByTitleIgnoreCase(title);
    }

    public Boolean deleteAudiobookByTitle(String title){
        if (audiobookRepository.existsAudiobookByTitleIgnoreCase(title)){
            audiobookRepository.deleteAudiobookByTitleIgnoreCase(title);
            return true;
        }
        return false;
    }

    public Audiobook createAudiobook(Audiobook audiobook){
        Audiobook newAudiobook = audiobookRepository.insert(audiobook);

        return newAudiobook;
    }

    public void updateAudiobook(Audiobook audiobook, String title){
        Optional<Audiobook> updatingAudiobook = audiobookRepository.findAudiobookByTitleIgnoreCase(title);

        if (updatingAudiobook.isPresent()) {
            ObjectId updatingAudiobookId = updatingAudiobook.get().getId();
            audiobook.setId(updatingAudiobookId);
            audiobookRepository.insert(audiobook);
        }
    }
}
