package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.Audiobook;
import dev.starzynski.audiostore.Service.AudiobookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/public/audiobooks")
    public ResponseEntity<List<Audiobook>> getAudiobooks(HttpServletRequest request) {
        return new ResponseEntity<List<Audiobook>>(audiobookService.getAllAudiobooks(), HttpStatus.OK);
    }

    @GetMapping("/public/audiobook/{title}")
    public ResponseEntity<Optional<Audiobook>> getAudiobook(@PathVariable String title) {
        return new ResponseEntity<Optional<Audiobook>> (audiobookService.getAudiobookByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/public/audiobook/search")
    public ResponseEntity<List<Audiobook>> searchAudiobooks(@RequestParam String search) {
        return new ResponseEntity<List<Audiobook>> (audiobookService.searchAudiobooks(search), HttpStatus.OK);
    }

    @DeleteMapping("/admin/audiobook/{title}")
    public ResponseEntity<Boolean> deleteAudiobook(@PathVariable String title){
        Boolean deleted = audiobookService.deleteAudiobookByTitle(title);

        if (deleted) { return new ResponseEntity<Boolean> (true, HttpStatus.OK); }

        else { return new ResponseEntity<Boolean> (false, HttpStatus.OK); }
    }

    @PostMapping(value = "/admin/audiobook", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> createAudiobook(
            @Validated @RequestParam("title") String title,
            @Validated @RequestParam("description") String description,
            @Validated @RequestParam("author") String author,
            @Validated @RequestParam("published_at_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date published_at_date,
            @Validated @RequestParam("genreName") String genreName,
            @Validated @RequestParam("coverImage") MultipartFile coverImage,
            @Validated @RequestParam("audioFile") MultipartFile audioFile)
    {
        return new ResponseEntity<Boolean> (audiobookService.createAudiobook(title, description, author, published_at_date, genreName, coverImage, audioFile), HttpStatus.OK);
    }

    @PatchMapping(value = "/admin/audiobook/{title}", consumes = "multipart/form-data")
    public ResponseEntity<Boolean> updateAudiobook(
            @PathVariable String title,
            @Validated @RequestParam(name = "description", required = false) String description,
            @Validated @RequestParam(name = "author", required = false) String author,
            @Validated @RequestParam(name = "genreName", required = false) String genreName,
            @Validated @RequestParam(name = "published_at_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") Date published_at_date)
    {
        return new ResponseEntity<Boolean> (audiobookService.updateAudiobook(title, description, author, published_at_date, genreName), HttpStatus.OK);
    }
}
