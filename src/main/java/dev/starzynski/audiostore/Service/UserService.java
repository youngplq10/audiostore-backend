package dev.starzynski.audiostore.Service;

import dev.starzynski.audiostore.Entity.User;
import dev.starzynski.audiostore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    public Optional<User> getOneUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User createUser(User user) {
        return userRepository.insert(user);
    }

    public String verify(User user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }

        return "Failed";
    }
}
