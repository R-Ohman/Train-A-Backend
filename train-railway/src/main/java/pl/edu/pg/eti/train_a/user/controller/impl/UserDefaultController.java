package pl.edu.pg.eti.train_a.user.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.user.controller.api.UserController;
import pl.edu.pg.eti.train_a.user.dto.SignInRequest;
import pl.edu.pg.eti.train_a.user.entity.User;
import pl.edu.pg.eti.train_a.user.service.api.UserService;

@RestController
@Log
public class UserDefaultController implements UserController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserDefaultController(
            UserService userService,
            UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void createUser(User user) {
        userService.create(user);
    }

    @Override
    public void signIn(SignInRequest request) {
        var user = userService.findByEmail(request.getEmail()).orElseThrow();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword())
        );
        userDetailsService.loadUserByUsername(user.getUsername());
    }
}
