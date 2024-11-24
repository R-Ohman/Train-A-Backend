package pl.edu.pg.eti.train_a.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.train_a.user.service.api.UserService;

@Service
public class DefaultUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public DefaultUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with UUID: " + username));
        return User
                .withUsername(user.getUsername())
                .password(user.getPassHash())
                .roles(user.getRole().getValue())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}