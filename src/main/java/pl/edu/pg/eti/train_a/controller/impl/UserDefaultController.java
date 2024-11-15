package pl.edu.pg.eti.train_a.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.controller.api.UserController;
import pl.edu.pg.eti.train_a.dto.GetUsersResponse;
import pl.edu.pg.eti.train_a.dto.PostUserRequest;
import pl.edu.pg.eti.train_a.function.RequestToUserFunction;
import pl.edu.pg.eti.train_a.function.UserToResponseFunction;
import pl.edu.pg.eti.train_a.service.user.UserService;

@RestController
@Log
public class UserDefaultController implements UserController {
    private final UserService userService;
    private final UserToResponseFunction userToResponseFunction;
    private final RequestToUserFunction requestToUser;

    @Autowired
    public UserDefaultController(
            UserService userService,
            UserToResponseFunction userToResponseFunction,
            RequestToUserFunction requestToUser
    ) {
        this.userService = userService;
        this.userToResponseFunction = userToResponseFunction;
        this.requestToUser = requestToUser;
    }

    @Override
    public GetUsersResponse getUsers() {
        return userToResponseFunction.apply(userService.findAll());
    }

    @Override
    public void postUser(PostUserRequest request) {
        userService.create(requestToUser.apply(request));
    }
}
