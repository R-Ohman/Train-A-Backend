package pl.edu.pg.eti.train_a.user.controller.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.pg.eti.train_a.user.dto.SignInRequest;
import pl.edu.pg.eti.train_a.user.entity.User;

public interface UserController {
    @PostMapping("api/event/user")
    @ResponseStatus(HttpStatus.CREATED)
    void createUser(@RequestBody User user);

    @PostMapping("api/event/signin")
    void signIn(@RequestBody @Valid SignInRequest request);
}
