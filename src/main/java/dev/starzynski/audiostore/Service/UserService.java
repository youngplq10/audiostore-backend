package dev.starzynski.audiostore.Service;

import dev.starzynski.audiostore.Entity.Audiobook;
import dev.starzynski.audiostore.Entity.User;
import dev.starzynski.audiostore.Repository.AudiobookRepository;
import dev.starzynski.audiostore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AudiobookRepository audiobookRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    public User getOneUser(String username) {
        return userRepository.findUserByUsername(username).orElseThrow();
    }

    public String createUser(User user) {
        userRepository.insert(user);
        return jwtService.generateToken(user.getUsername());
    }

    public String verify(User user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }

        else {
            System.out.println("failed");
        }
        return "Failed";
    }

    public String likeAudiobook(String username, String title) {
        String newTitle = title.replaceAll("-", " ");

        User user = userRepository.findUserByUsernameIgnoreCase(username);

        Audiobook audiobook = audiobookRepository.findAudiobookByTitleIgnoreCase(newTitle).orElseThrow();

        user.getLikedAudiobooks().add(audiobook);

        userRepository.save(user);

        return "Liked";
    }

    public String unlikeAudiobook(String username, String title) {
        String newTitle = title.replaceAll("-", " ");

        User user = userRepository.findUserByUsernameIgnoreCase(username);

        Audiobook audiobook = audiobookRepository.findAudiobookByTitleIgnoreCase(newTitle).orElseThrow();

        for (int i=0; i<user.getLikedAudiobooks().size(); i++) {
            if (Objects.equals(user.getLikedAudiobooks().get(i).getTitle(), audiobook.getTitle())) {
                user.getLikedAudiobooks().remove(user.getLikedAudiobooks().get(i));
            }
        }

        userRepository.save(user);

        return "Unliked";
    }
}
