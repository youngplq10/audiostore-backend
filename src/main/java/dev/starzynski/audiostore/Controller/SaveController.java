package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.Save;
import dev.starzynski.audiostore.Service.SaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class SaveController {
    @Autowired
    private SaveService saveService;

    @PostMapping("/save")
    public String saveAudiobook(@RequestParam String username, @RequestParam String token, @RequestParam String title){
        Save save = new Save();
        save.setTitle(title);

        return saveService.saveAudiobook(save, username);
    }
}
