package dev.starzynski.audiostore.Service;

import dev.starzynski.audiostore.Entity.Audiobook;
import dev.starzynski.audiostore.Entity.Genre;
import dev.starzynski.audiostore.Repository.AudiobookRepository;
import dev.starzynski.audiostore.Repository.GenreRepository;
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
    private GenreRepository genreRepository;

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

        System.out.println(audiobook.getGenre().getName());

        Genre genre = genreRepository.findByNameIgnoreCase(audiobook.getGenre().getName());

        audiobook.setGenre(genre);

        audiobookRepository.insert(audiobook);

        genre.getAudiobooks().add(audiobook);
        genreRepository.save(genre);

        return audiobook;
    }

    public void updateAudiobook(Audiobook audiobook, String title){
        Audiobook updatingAudiobook = audiobookRepository.findAudiobookByTitleIgnoreCase(title).orElseThrow(null);

        updatingAudiobook.setTitle(audiobook.getTitle());
        updatingAudiobook.setDescription(audiobook.getDescription());
        updatingAudiobook.setAuthor(audiobook.getAuthor());
        updatingAudiobook.setCoverLink(audiobook.getCoverLink());
        updatingAudiobook.setAudioLink(audiobook.getAudioLink());
        updatingAudiobook.setGenre(audiobook.getGenre());
        updatingAudiobook.setDuration(audiobook.getDuration());
        updatingAudiobook.setPublished_at_date(audiobook.getPublished_at_date());

        audiobookRepository.save(updatingAudiobook);
    }
}
