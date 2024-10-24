package pl.edu.pg.eti.train_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Carriage;
import pl.edu.pg.eti.train_a.entity.Station;
import pl.edu.pg.eti.train_a.entity.User;
import pl.edu.pg.eti.train_a.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void create(User user) {
        this.userRepository.save(user);
    }

    public void delete(UUID id) {
        this.userRepository.findById(id).ifPresent(userRepository::delete);
    }
}
