package pl.edu.pg.eti.train_a.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.controller.api.UserController;
import pl.edu.pg.eti.train_a.dto.GetUsersResponse;
import pl.edu.pg.eti.train_a.function.UserToResponseFunction;
import pl.edu.pg.eti.train_a.service.user.UserServiceImpl;

@RestController
@Log
public class UserDefaultController implements UserController {
    private final UserServiceImpl userService;
    private final UserToResponseFunction userToResponseFunction;

    @Autowired
    public UserDefaultController(UserServiceImpl userService, UserToResponseFunction userToResponseFunction) {
        this.userService = userService;
        this.userToResponseFunction = userToResponseFunction;
    }

    @Override
    public GetUsersResponse getUsers() {
        return userToResponseFunction.apply(userService.findAll());
    }
}
