package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.Save;
import dev.starzynski.audiostore.Entity.User;
import dev.starzynski.audiostore.Service.JWTService;
import dev.starzynski.audiostore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<User> (userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user){
        return userService.verify(user);
    }

}
