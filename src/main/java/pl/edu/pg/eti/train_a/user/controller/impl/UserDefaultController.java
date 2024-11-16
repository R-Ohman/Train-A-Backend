package pl.edu.pg.eti.train_a.user.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.user.controller.api.UserController;
import pl.edu.pg.eti.train_a.user.dto.*;
import pl.edu.pg.eti.train_a.user.function.RequestToUserFunction;
import pl.edu.pg.eti.train_a.user.function.UserToResponseFunction;
import pl.edu.pg.eti.train_a.user.service.api.UserService;

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

    @Override
    public PutUserResponse putUser(PutUserRequest request) {
        var user = requestToUser.apply(1, request); // TODO: get user id from session
        userService.create(user);
        return PutUserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    @Override
    public void putPassword(PutPasswordRequest request) {
        var user = userService.findById(1); // TODO: get user id from session
        user.setPassHash(request.getPassword());
        userService.create(user);
    }
}
