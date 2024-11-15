package pl.edu.pg.eti.train_a.user.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.user.dto.GetUsersResponse;
import pl.edu.pg.eti.train_a.user.dto.PostUserRequest;

public interface UserController {
    @GetMapping("api/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetUsersResponse getUsers();

    @PostMapping("api/signup")
    @ResponseStatus(HttpStatus.CREATED)
    void postUser(@RequestBody PostUserRequest request);
}
