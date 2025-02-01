package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.Audiobook;
import dev.starzynski.audiostore.Entity.Genre;
import dev.starzynski.audiostore.Service.AudiobookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class AudiobookController {
    @Autowired
    private AudiobookService audiobookService;

    @Value("${upload.directory}")
    public String uploadDirectory = "/uploads/";

    @GetMapping("/audiobooks")
    public ResponseEntity<List<Audiobook>> getAudiobooks(HttpServletRequest request) {

        return new ResponseEntity<List<Audiobook>>(audiobookService.getAllAudiobooks(), HttpStatus.OK);
    }

    @GetMapping("/audiobook/{title}")
    public ResponseEntity<Optional<Audiobook>> getAudiobook(@PathVariable String title) {
        String newTitle = title.replaceAll("-", " ");

        return new ResponseEntity<Optional<Audiobook>> (audiobookService.getAudiobookByTitle(newTitle), HttpStatus.OK);
    }

    @DeleteMapping("/audiobook/{title}")
    public ResponseEntity<String> deleteAudiobook(@PathVariable String title){
        String newTitle = title.replaceAll("-", " ");

        Boolean deleted = audiobookService.deleteAudiobookByTitle(newTitle);

        if (deleted) { return new ResponseEntity<String> ("Deleted.", HttpStatus.OK); }

        else { return new ResponseEntity<String> ("Audiobook with this title doesn't exist.", HttpStatus.OK); }
    }

    @PostMapping(value = "/audiobook", consumes = "multipart/form-data")
    public ResponseEntity<String> createAudiobook(
            @Validated @RequestParam("title") String title,
            @Validated @RequestParam("description") String description,
            @Validated @RequestParam("genre") String genreName,
            @Validated @RequestParam("author") String author,
            @Validated @RequestParam("duration") Integer duration,
            @Validated @RequestParam("published_at_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date published_at_date,
            @Validated @RequestParam("coverImage") MultipartFile coverImage,
            @Validated @RequestParam("audioFile") MultipartFile audioFile
    ){
        try{
            Audiobook newAudiobook = new Audiobook();

            Genre newGenre = new Genre();
            newGenre.setName(genreName);

            newAudiobook.setTitle(title);
            newAudiobook.setDescription(description);
            newAudiobook.setAuthor(author);
            newAudiobook.setGenre(newGenre);
            newAudiobook.setDuration(duration);
            newAudiobook.setPublished_at_date(published_at_date);

            try {
                File directory = new File(uploadDirectory);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                byte[] audioBytes = audioFile.getBytes();
                byte[] coverBytes = coverImage.getBytes();

                Path coverPath = Paths.get(uploadDirectory + newAudiobook.getId() + coverImage.getOriginalFilename());

                Path audioPath = Paths.get(uploadDirectory + newAudiobook.getId() + audioFile.getOriginalFilename());

                Files.write(coverPath, coverBytes);
                Files.write(audioPath, audioBytes);

                newAudiobook.setCoverLink("/uploads/" + newAudiobook.getId() + coverImage.getOriginalFilename());
                newAudiobook.setAudioLink("/uploads/" + newAudiobook.getId() + audioFile.getOriginalFilename());

            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

            Audiobook newAudiobook2 = audiobookService.createAudiobook(newAudiobook);
            return new ResponseEntity<String> ("created: " + newAudiobook2.getTitle(), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<String> (e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/audiobook/{title}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateAudiobook(
            @PathVariable String title,
            @Validated @RequestParam(name = "description", required = false) String description,
            @Validated @RequestParam(name = "author", required = false) String author,
            @Validated @RequestParam(name = "genre", required = false) Genre genre,
            @Validated @RequestParam(name = "duration", required = false) Integer duration,
            @Validated @RequestParam(name = "published_at_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") Date published_at_date,
            @Validated @RequestParam(name = "coverImage", required = false) MultipartFile coverImage,
            @Validated @RequestParam(name = "audioFile", required = false) MultipartFile audioFile
            ){

        String newTitle = title.replaceAll("-", " ");

        try {
            Optional<Audiobook> audiobook = audiobookService.getAudiobookByTitle(newTitle);
            Audiobook newAudiobook = new Audiobook();

            if(title.isEmpty()) { newAudiobook.setTitle(title); } else { newAudiobook.setTitle(audiobook.get().getTitle()); }
            if(description != null) { newAudiobook.setDescription(description); } else { newAudiobook.setDescription(audiobook.get().getDescription()); }
            if(author != null) { newAudiobook.setAuthor(author); } else { newAudiobook.setAuthor(audiobook.get().getAuthor()); }
            if(genre != null) { newAudiobook.setGenre(genre); } else { newAudiobook.setGenre(audiobook.get().getGenre()); }
            if(duration != null) { newAudiobook.setDuration(duration); } else { newAudiobook.setDuration(audiobook.get().getDuration()); }
            if(published_at_date != null) { newAudiobook.setPublished_at_date(published_at_date); } else { newAudiobook.setPublished_at_date(audiobook.get().getPublished_at_date()); }

            if (coverImage != null) {
                try {
                    File directory = new File(uploadDirectory);

                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    if (uploadDirectory + audiobook.get().getId() + coverImage.getOriginalFilename() != audiobook.get().getCoverLink()) {
                        byte[] coverBytes = coverImage.getBytes();
                        Path coverPath = Paths.get(uploadDirectory + audiobook.get().getId() + coverImage.getOriginalFilename());
                        Files.write(coverPath, coverBytes);
                        newAudiobook.setCoverLink(uploadDirectory + audiobook.get().getId() + coverImage.getOriginalFilename());
                    } else {
                        newAudiobook.setCoverLink(audiobook.get().getCoverLink());
                    }
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                    return new ResponseEntity<String> (e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            else {
                newAudiobook.setCoverLink(audiobook.get().getCoverLink());
            }

            if (audioFile != null) {
                try {
                    File directory = new File(uploadDirectory);

                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    if (uploadDirectory + audiobook.get().getId() + audioFile.getOriginalFilename() != audiobook.get().getAudioLink()) {
                        byte[] audioBytes = audioFile.getBytes();
                        Path audioPath = Paths.get(uploadDirectory + audiobook.get().getId() + audioFile.getOriginalFilename());
                        Files.write(audioPath, audioBytes);
                        newAudiobook.setAudioLink(uploadDirectory + audiobook.get().getId() + audioFile.getOriginalFilename());
                    } else {
                        newAudiobook.setAudioLink(audiobook.get().getAudioLink());
                    }
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                    return new ResponseEntity<String> (e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            else {
                newAudiobook.setAudioLink(audiobook.get().getAudioLink());
            }

            audiobookService.updateAudiobook(newAudiobook, audiobook.get().getTitle());
            return new ResponseEntity<String> ("created", HttpStatus.CREATED);
        } catch (Exception e){
            System.out.print(e.getMessage());
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
