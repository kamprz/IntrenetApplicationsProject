package wat.semestr7.ai.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.entities.User;
import wat.semestr7.ai.repositories.UserRepository;

@Service
public class UserService
{
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveNewClient(User user)
    {
        userRepository.save(user);
    }
}
