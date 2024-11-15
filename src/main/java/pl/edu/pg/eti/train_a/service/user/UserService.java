package pl.edu.pg.eti.train_a.service.user;

import pl.edu.pg.eti.train_a.entity.User;

import java.util.List;

public interface UserService {
    void create(User user);

    void delete(int id);

    List<User> findAll();

    User findById(int id);

    User findByEmailWithDetails(String email);
}
