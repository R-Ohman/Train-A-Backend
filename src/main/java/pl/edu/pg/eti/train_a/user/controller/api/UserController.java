package pl.edu.pg.eti.train_a.user.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.user.dto.*;

public interface UserController {
    @GetMapping("api/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetUsersResponse getUsers();

    @PostMapping("api/signup")
    @ResponseStatus(HttpStatus.CREATED)
    void postUser(@RequestBody PostUserRequest request);

    @PutMapping("api/profile")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    PutUserResponse putUser(@RequestBody PutUserRequest request);

    @PutMapping("api/profile/password")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    void putPassword(@RequestBody PutPasswordRequest request);
}
