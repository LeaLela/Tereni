package ba.sum.fsre.tereni.services;

import ba.sum.fsre.tereni.models.Role;
import ba.sum.fsre.tereni.models.User;
import ba.sum.fsre.tereni.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        user.getRoles().add(Role.KORISNIK);
        userRepository.save(user);
    }
}

