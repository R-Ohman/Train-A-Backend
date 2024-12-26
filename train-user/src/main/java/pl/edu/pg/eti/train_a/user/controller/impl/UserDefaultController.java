package pl.edu.pg.eti.train_a.user.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.exception.CustomResponseStatusException;
import pl.edu.pg.eti.train_a.security.JwtUtil;
import pl.edu.pg.eti.train_a.user.controller.api.UserController;
import pl.edu.pg.eti.train_a.user.dto.*;
import pl.edu.pg.eti.train_a.user.event.api.UserEventRepository;
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
    private final UserEventRepository userEventRepository;

    @Autowired
    public UserDefaultController(
            UserService userService,
            UserToResponseFunction userToResponseFunction,
            RequestToUserFunction requestToUser,
            JwtUtil jwtUtil,
            UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager,
            UserEventRepository userEventRepository
    ) {
        this.userService = userService;
        this.userToResponseFunction = userToResponseFunction;
        this.requestToUser = requestToUser;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userEventRepository = userEventRepository;
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
    public void signUp(PostUserRequest request) {
        try {
            userService.create(requestToUser.apply(request));
        } catch (IllegalArgumentException e) {
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "invalidUniqueKey", "User already exists");
        }
    }

    @Override
    public UserProfileResponse updateUser(PutUserRequest request) {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "invalidUniqueKey", "Email already exists");
        }
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
    public void updatePassword(PutPasswordRequest request) {
        var user = userService.getCurrentUser().orElseThrow();
        user.setPassword(request.getPassword());
        userService.create(user);
    }

    @Override
    public SignInResponse signIn(SignInRequest request) {
        try {
            System.out.println(userService.findAll());
            var user = userService.findByEmail(request.getEmail()).orElseThrow();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());

            System.out.println("[user] JWT: " + jwt);

            userEventRepository.signIn(request);

            System.out.println("[user] User signed in: " + user.getEmail());
            return SignInResponse.builder().token(jwt).build();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "userNotFound", "User is not found");
        }
    }

    @Override
    public void logout() {
        // TODO: (?) jwt is stateless
    }
}
