package DoBattle.service;

import DoBattle.domain.User;
import DoBattle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }
    public boolean isIdentifyAvailable(String identify) {
        return userRepository.findByIdentify(identify) == null;
    }

}