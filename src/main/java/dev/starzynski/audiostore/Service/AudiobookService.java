package dev.starzynski.audiostore.Service;

import dev.starzynski.audiostore.Entity.Audiobook;
import dev.starzynski.audiostore.Entity.Genre;
import dev.starzynski.audiostore.Repository.AudiobookRepository;
import dev.starzynski.audiostore.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AudiobookService {
    @Autowired
    private AudiobookRepository audiobookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${upload.directory}")
    public String uploadDirectory = "/uploads/";

    public List<Audiobook> getAllAudiobooks() {
        return audiobookRepository.findAll();
    }

    public Optional<Audiobook> getAudiobookByTitle(String title) {
        String newTitle = title.replaceAll("-", " ");
        return audiobookRepository.findAudiobookByTitleIgnoreCase(newTitle);
    }

    public Boolean deleteAudiobookByTitle(String title){
        String newTitle = title.replaceAll("-", " ");

        if (audiobookRepository.existsAudiobookByTitleIgnoreCase(newTitle)){
            audiobookRepository.deleteAudiobookByTitleIgnoreCase(newTitle);
            return true;
        }

        return false;
    }

    public Boolean createAudiobook(String title, String description, String author, Date published_at_date, String genreName, MultipartFile coverImage, MultipartFile audioFile){
        Audiobook audiobook = new Audiobook(title, description, author, published_at_date);

        try{
            Genre genre = genreRepository.findByNameIgnoreCase(genreName);

            audiobook.setGenre(genre);

            try {
                File directory = new File(uploadDirectory);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                byte[] audioBytes = audioFile.getBytes();
                byte[] coverBytes = coverImage.getBytes();

                Path coverPath = Paths.get(uploadDirectory + audiobook.getId() + coverImage.getOriginalFilename());

                Path audioPath = Paths.get(uploadDirectory + audiobook.getId() + audioFile.getOriginalFilename());

                Files.write(coverPath, coverBytes);
                Files.write(audioPath, audioBytes);

                audiobook.setCoverLink("/uploads/" + audiobook.getId() + coverImage.getOriginalFilename());
                audiobook.setAudioLink("/uploads/" + audiobook.getId() + audioFile.getOriginalFilename());

            } catch (Exception e) {
                return false;
            }

            audiobookRepository.insert(audiobook);

            genre.getAudiobooks().add(audiobook);
            genreRepository.save(genre);

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Boolean updateAudiobook(String title, String description, String author, Date published_at_date, String genreName){
        String newTitle = title.replaceAll("-", " ");

        Audiobook updatingAudiobook = audiobookRepository.findAudiobookByTitleIgnoreCase(newTitle).orElseThrow();

        if (!Objects.equals(description, updatingAudiobook.getDescription())){ updatingAudiobook.setDescription(description); }
        if (!Objects.equals(author, updatingAudiobook.getAuthor())){ updatingAudiobook.setAuthor(author); }
        if (!Objects.equals(published_at_date, updatingAudiobook.getPublished_at_date())){ updatingAudiobook.setPublished_at_date(published_at_date); }
        if (!Objects.equals(genreName, updatingAudiobook.getGenre().getName())){
            Genre newGenre = genreRepository.findByNameIgnoreCase(genreName);
            updatingAudiobook.setGenre(newGenre);
            genreRepository.save(newGenre);
        }
        audiobookRepository.save(updatingAudiobook);

        return true;
    }

    public List<Audiobook> searchAudiobooks(String search) {
        return audiobookRepository.searchAudiobooks(search);
    }
}
