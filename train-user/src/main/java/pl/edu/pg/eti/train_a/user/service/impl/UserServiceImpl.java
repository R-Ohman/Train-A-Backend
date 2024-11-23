package pl.edu.pg.eti.train_a.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.user.entity.User;
import pl.edu.pg.eti.train_a.user.repository.api.UserRepository;
import pl.edu.pg.eti.train_a.user.service.api.UserService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public  Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public  Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
            throw new IllegalArgumentException("invalidUniqueKey");
        }
        user.setPassHash(passwordEncoder.encode(user.getPassHash()));
        this.userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        this.userRepository.findById(id).ifPresent(userRepository::delete);
    }

    @Override
    public Optional<User> getCurrentUser() {
        try {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var username = authentication.getName();
            return this.findByUsername(username);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
