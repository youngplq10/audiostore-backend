package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.Genre;
import dev.starzynski.audiostore.Service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getAllGenres(){
        return new ResponseEntity<List<Genre>> (genreService.getAllGenres(), HttpStatus.OK);
    }

    @PostMapping("/genre")
    public ResponseEntity<String> createGenre(@Validated @RequestParam String name){
        return new ResponseEntity<String> (genreService.createGenre(name), HttpStatus.CREATED);
    }
}
