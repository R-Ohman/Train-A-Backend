package pl.edu.pg.eti.train_a.user.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.user.dto.PostUserRequest;
import pl.edu.pg.eti.train_a.user.entity.User;
import pl.edu.pg.eti.train_a.user.entity.UserRole;

import java.util.function.Function;

@Component
public class RequestToUserFunction implements Function<PostUserRequest, User> {
    @Override
    public User apply(PostUserRequest postUserRequest) {
        return User.builder()
                .email(postUserRequest.getEmail())
                .passHash(postUserRequest.getPassword())
                .role(UserRole.USER)
                .build();
    }
}