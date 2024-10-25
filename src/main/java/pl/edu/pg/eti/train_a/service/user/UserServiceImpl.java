package pl.edu.pg.eti.train_a.service.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.User;
import pl.edu.pg.eti.train_a.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User findByEmailWithDetails(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.getOrders().size();
        return user;
    }

    public void create(User user) {
        this.userRepository.save(user);
    }

    public void delete(UUID id) {
        this.userRepository.findById(id).ifPresent(userRepository::delete);
    }
}
