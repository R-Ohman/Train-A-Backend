package pl.edu.pg.eti.train_a.user.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pg.eti.train_a.security.JwtUtil;
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
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserDefaultController(
            UserService userService,
            UserToResponseFunction userToResponseFunction,
            RequestToUserFunction requestToUser,
            JwtUtil jwtUtil,
            UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.userToResponseFunction = userToResponseFunction;
        this.requestToUser = requestToUser;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public GetUsersResponse getUsers() {
        return userToResponseFunction.apply(userService.findAll());
    }

    @Override
    public UserProfileResponse getUser() {
        var user = userService.getCurrentUser().orElseThrow();
        return UserProfileResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    @Override
    public void postUser(PostUserRequest request) {
        userService.create(requestToUser.apply(request));
    }

    @Override
    public UserProfileResponse putUser(PutUserRequest request) {
        var user = userService.getCurrentUser().orElseThrow();
        var updatedUser = requestToUser.apply(user.getId(), request);
        userService.create(updatedUser);
        return UserProfileResponse.builder()
                .email(updatedUser.getEmail())
                .name(updatedUser.getName())
                .role(updatedUser.getRole())
                .build();
    }

    @Override
    public void putPassword(PutPasswordRequest request) {
        var user = userService.getCurrentUser().orElseThrow();
        user.setPassHash(request.getPassword());
        userService.create(user);
    }

    @Override
    public SignInResponse signIn(SignInRequest request) throws Exception {
        try {
            var user = userService.findByEmail(request.getEmail());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());

            return SignInResponse.builder().token(jwt).build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect username or password", e);
        }
    }
}
