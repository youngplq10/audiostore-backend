package dev.starzynski.audiostore.Service;

import dev.starzynski.audiostore.Entity.Audiobook;
import dev.starzynski.audiostore.Repository.AudiobookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudiobookService {
    private AudiobookRepository audiobookRepository;

    public List<Audiobook> getAllAudiobooks() {
        return audiobookRepository.findAll();
    }
}
