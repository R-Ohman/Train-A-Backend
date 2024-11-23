package pl.edu.pg.eti.train_a.user.service.api;

import pl.edu.pg.eti.train_a.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void create(User user);

    void delete(int id);

    List<User> findAll();

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> getCurrentUser();
}
