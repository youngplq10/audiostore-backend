package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.Audiobook;
import dev.starzynski.audiostore.Repository.AudiobookRepository;
import dev.starzynski.audiostore.Service.AudiobookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class AudiobookController {
    @Autowired
    private AudiobookService audiobookService;

    @GetMapping("audiobooks")
    public ResponseEntity<List<Audiobook>> getAudiobooks() {
        return new ResponseEntity<List<Audiobook>>(audiobookService.getAllAudiobooks(), HttpStatus.OK);
    }
}
