package dev.starzynski.audiostore.Service;

import dev.starzynski.audiostore.Entity.User;
import dev.starzynski.audiostore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Optional<User> getOneUser(String username) {
        return userRepository.findUserByUsername(username);
    }
}
