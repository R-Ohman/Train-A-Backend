package pl.edu.pg.eti.train_a.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.user.entity.User;
import pl.edu.pg.eti.train_a.user.entity.UserRole;
import pl.edu.pg.eti.train_a.user.service.api.UserService;

@Component
public class InitializeData {
    private final UserService userService;
    private final RestTemplate restTemplate;

    @Autowired
    public InitializeData(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterPropertiesSet() throws InterruptedException {
        if (userService.findAll().isEmpty()) {

            while (true) {
                try {
                    restTemplate.getForEntity("/api", Void.class);
                    break;
                } catch (HttpClientErrorException e) {
                    break;
                } catch (Exception e) {
                    System.out.println("Waiting for railway service to start...");
                    Thread.sleep(1000);
                }
            }

            userService.create(User.builder()
                    .email("admin@admin.com")
                    .password("my-password")
                    .role(UserRole.MANAGER)
                    .build());
        }
    }
}