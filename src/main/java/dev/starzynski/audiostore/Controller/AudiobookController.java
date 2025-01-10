package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.Audiobook;
import dev.starzynski.audiostore.Service.AudiobookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class AudiobookController {
    @Autowired
    private AudiobookService audiobookService;

    @Value("${upload.directory}")
    public String uploadDirectory;

    @GetMapping("audiobooks")
    public ResponseEntity<List<Audiobook>> getAudiobooks() {
        return new ResponseEntity<List<Audiobook>>(audiobookService.getAllAudiobooks(), HttpStatus.OK);
    }

    @GetMapping("/audiobook/{title}")
    public ResponseEntity<Optional<Audiobook>> getAudiobook(@PathVariable String title) {
        String newTitle = title.replaceAll("-", " ");
        System.out.println(newTitle);

        return new ResponseEntity<Optional<Audiobook>> (audiobookService.getAudiobookByTitle(newTitle), HttpStatus.OK);
    }

    @DeleteMapping("/audiobook/{title}")
    public ResponseEntity<String> deleteAudiobook(@PathVariable String title){
        String newTitle = title.replaceAll("-", " ");

        Boolean deleted = audiobookService.deleteAudiobookByTitle(newTitle);

        if (deleted) { return new ResponseEntity<String> ("Deleted.", HttpStatus.OK); }

        else { return new ResponseEntity<String> ("Audiobook with this title doesn't exist.", HttpStatus.OK); }
    }

    @PostMapping(value = "/audiobook/create", consumes = "multipart/form-data")
    public ResponseEntity<String> createAudiobook(
            @Validated @RequestParam("title") String title,
            @Validated @RequestParam("description") String description,
            @Validated @RequestParam("genre") List<String> genre,
            @Validated @RequestParam("duration") int duration,
            @Validated @RequestParam("published_at_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") Date published_at_date,
            @Validated @RequestParam("coverImage") MultipartFile coverImage,
            @Validated @RequestParam("audioFile") MultipartFile audioFile
    ){
        try{
            Audiobook newAudiobook = new Audiobook();

            newAudiobook.setTitle(title);
            newAudiobook.setDescription(description);
            newAudiobook.setGenre(genre);
            newAudiobook.setDuration(duration);
            newAudiobook.setPublished_at_date(published_at_date);

            try {
                File directory = new File(uploadDirectory);

                if (!directory.exists()) {
                    directory.mkdirs();
                }
                byte[] audioBytes = audioFile.getBytes();
                byte[] coverBytes = coverImage.getBytes();

                Path coverPath = Paths.get(uploadDirectory + coverImage.getOriginalFilename());
                Path audioPath = Paths.get(uploadDirectory + audioFile.getOriginalFilename());

                Files.write(coverPath, coverBytes);
                Files.write(audioPath, audioBytes);

                newAudiobook.setCoverLink(uploadDirectory + coverImage.getOriginalFilename());
                newAudiobook.setAudioLink(uploadDirectory + audioFile.getOriginalFilename());

            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

            newAudiobook.setCoverLink(coverImage.getOriginalFilename());
            newAudiobook.setAudioLink(audioFile.getOriginalFilename());

            Audiobook newAudiobook2 = audiobookService.createAudiobook(newAudiobook);
            return new ResponseEntity<String> ("created: " + newAudiobook2.getTitle(), HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<String> (e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/audiobook/{title}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateAudiobook(
            @PathVariable String title,
            @Validated @RequestParam("description") String description,
            @Validated @RequestParam("genre") List<String> genre,
            @Validated @RequestParam("duration") int duration,
            @Validated @RequestParam("published_at_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") Date published_at_date,
            @Validated @RequestParam("coverImage") MultipartFile coverImage,
            @Validated @RequestParam("audioFile") MultipartFile audioFile
            ){

        String newTitle = title.replaceAll("-", " ");

        try {
            Audiobook newAudiobook = new Audiobook();

            newAudiobook.setTitle(title);
            newAudiobook.setDescription(description);
            newAudiobook.setGenre(genre);
            newAudiobook.setDuration(duration);
            newAudiobook.setPublished_at_date(published_at_date);


                File directory = new File(uploadDirectory);

                if (!directory.exists()) {
                    directory.mkdirs();
                }
                byte[] audioBytes = audioFile.getBytes();
                byte[] coverBytes = coverImage.getBytes();

                Path coverPath = Paths.get(uploadDirectory + coverImage.getOriginalFilename());
                Path audioPath = Paths.get(uploadDirectory + audioFile.getOriginalFilename());

                Files.write(coverPath, coverBytes);
                Files.write(audioPath, audioBytes);

                newAudiobook.setCoverLink(uploadDirectory + coverImage.getOriginalFilename());
                newAudiobook.setAudioLink(uploadDirectory + audioFile.getOriginalFilename());

                newAudiobook.setCoverLink(coverImage.getOriginalFilename());
                newAudiobook.setAudioLink(audioFile.getOriginalFilename());

                audiobookService.updateAudiobook(newAudiobook, newTitle);
                return new ResponseEntity<String> ("Updated", HttpStatus.OK);


        } catch (Exception e){
            System.out.print(e.getMessage());
            return new ResponseEntity<String> (e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
