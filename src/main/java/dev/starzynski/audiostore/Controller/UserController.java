package dev.starzynski.audiostore.Controller;

import dev.starzynski.audiostore.Entity.User;
import dev.starzynski.audiostore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
