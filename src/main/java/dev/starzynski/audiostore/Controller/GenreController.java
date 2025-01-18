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
@CrossOrigin(origins = "http://localhost:3000")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getAllGenres(){
        return new ResponseEntity<List<Genre>> (genreService.getAllGenres(), HttpStatus.OK);
    }

    @GetMapping("/genre/{name}")
    public ResponseEntity<Genre> getGenreNamed(@PathVariable String name){
        String newName = name.replaceAll("-", " ");

        return new ResponseEntity<Genre> (genreService.getGenreByName(newName), HttpStatus.OK);
    }

    @PostMapping("/genre")
    public ResponseEntity<String> createGenre(@Validated @RequestParam String name){
        return new ResponseEntity<String> (genreService.createGenre(name), HttpStatus.CREATED);
    }
}
