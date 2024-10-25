package pl.edu.pg.eti.train_a.service.user;

import pl.edu.pg.eti.train_a.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void create(User user);

    void delete(UUID id);

    List<User> findAll();

    User findById(UUID id);
}
