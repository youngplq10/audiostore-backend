package dev.starzynski.audiostore.Service;

import dev.starzynski.audiostore.Entity.Save;
import dev.starzynski.audiostore.Entity.User;
import dev.starzynski.audiostore.Repository.SaveRepository;
import dev.starzynski.audiostore.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaveService {
    @Autowired
    private SaveRepository saveRepository;
    @Autowired
    private UserRepository userRepository;

    public String saveAudiobook(Save save, String username){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByUsernameIgnoreCase(username));

        if(userOptional.isPresent()) {
            User user = userOptional.get();

            save.setUsername(username);

            saveRepository.insert(save);

            user.getSaves().add(save);

            userRepository.save(user);

            return "saved";
        }


        return "failed";
    }
}
