package dev.starzynski.audiostore.Service;

import dev.starzynski.audiostore.Entity.Genre;
import dev.starzynski.audiostore.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres(){
        return genreRepository.findAll();
    }

    public String createGenre(String name){
        Genre genre = new Genre();
        genre.setName(name);

        genreRepository.insert(genre);

        return "created";
    }
}
