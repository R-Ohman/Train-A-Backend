package pl.edu.pg.eti.train_a.initialize;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.user.entity.User;
import pl.edu.pg.eti.train_a.user.entity.UserRole;
import pl.edu.pg.eti.train_a.user.service.api.UserService;

@Component
public class InitializeData implements InitializingBean {
    private final UserService userService;

    @Autowired
    public InitializeData(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() {
        if (userService.findAll().isEmpty()) {
            userService.create(User.builder()
                    .email("admin@admin.com")
                    .password("my-password")
                    .role(UserRole.MANAGER)
                    .build());
        }
    }
}