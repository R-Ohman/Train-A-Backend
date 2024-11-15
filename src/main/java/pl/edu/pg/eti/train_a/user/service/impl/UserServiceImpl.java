package pl.edu.pg.eti.train_a.user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.user.entity.User;
import pl.edu.pg.eti.train_a.user.repository.api.UserRepository;
import pl.edu.pg.eti.train_a.user.service.api.UserService;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User findByEmailWithDetails(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.getOrders().size();
        return user;
    }

    @Override
    public void create(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        this.userRepository.findById(id).ifPresent(userRepository::delete);
    }
}
