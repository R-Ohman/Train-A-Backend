package pl.edu.pg.eti.train_a.user.event.api;

import pl.edu.pg.eti.train_a.user.dto.SignInRequest;
import pl.edu.pg.eti.train_a.user.entity.User;

public interface UserEventRepository {
    void create(User user);

    void signIn(SignInRequest request);
}
