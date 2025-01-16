package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.User;
import dev.starzynski.audiostore.Service.JWTService;
import dev.starzynski.audiostore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{username}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable String username){
        return new ResponseEntity<Optional<User>> (userService.getOneUser(username), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<User> (userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User user){
        return userService.verify(user);
    }

}
