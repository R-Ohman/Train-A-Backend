package pl.edu.pg.eti.train_a.user.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.user.dto.PostUserRequest;
import pl.edu.pg.eti.train_a.user.dto.PutUserRequest;
import pl.edu.pg.eti.train_a.user.entity.User;
import pl.edu.pg.eti.train_a.user.entity.UserRole;
import pl.edu.pg.eti.train_a.user.service.api.UserService;

import java.util.function.Function;

@Component
public class RequestToUserFunction implements Function<PostUserRequest, User> {
    private final UserService userService;

    @Autowired
    public RequestToUserFunction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User apply(PostUserRequest postUserRequest) {
        return User.builder()
                .email(postUserRequest.getEmail())
                .passHash(postUserRequest.getPassword())
                .role(UserRole.USER)
                .build();
    }

    public User apply(int id, PutUserRequest putUserRequest) {
        var user = userService.findById(id).orElseThrow();
        return User.builder()
                .id(id)
                .name(putUserRequest.getName())
                .email(putUserRequest.getEmail())
                .passHash(user.getPassHash())
                .role(user.getRole())
                .build();
    }
}
