package pl.edu.pg.eti.train_a.user.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.user.dto.*;

public interface UserController {
    @GetMapping("api/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetUsersResponse getUsers();

    @GetMapping("api/profile")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UserProfileResponse getUser();

    @PostMapping("api/signup")
    @ResponseStatus(HttpStatus.CREATED)
    void postUser(@RequestBody PostUserRequest request);

    @PostMapping("api/signin")
    SignInResponse signIn(@RequestBody SignInRequest request) throws Exception;

    @PutMapping("api/profile")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    UserProfileResponse putUser(@RequestBody PutUserRequest request);

    @PutMapping("api/profile/password")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    void putPassword(@RequestBody PutPasswordRequest request);

    @DeleteMapping("api/logout")
    @ResponseStatus(HttpStatus.OK)
    void logout();
}
