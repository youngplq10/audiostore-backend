package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.User;
import dev.starzynski.audiostore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/public/register")
    public String createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PostMapping("/public/login")
    public String loginUser(@RequestBody User user){
        return userService.verify(user);
    }

    @PostMapping("/auth/like")
    public ResponseEntity<String> likeAudiobook(@RequestParam String username, @RequestParam String title) {
        return new ResponseEntity<String> (userService.likeAudiobook(username, title), HttpStatus.OK);
    }

    @PostMapping("/auth/unlike")
    public ResponseEntity<String> unLikeAudiobook(@RequestParam String username, @RequestParam String title) {
        return new ResponseEntity<String> (userService.unlikeAudiobook(username, title), HttpStatus.OK);
    }

    @GetMapping("/auth/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return new ResponseEntity<User> (userService.getOneUser(username), HttpStatus.OK);
    }
}
