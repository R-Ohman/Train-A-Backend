package pl.edu.pg.eti.train_a.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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
    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public InitializeData(UserService userService, RestTemplate restTemplate, LoadBalancerClient loadBalancerClient) {
        this.userService = userService;
        this.restTemplate = restTemplate;
        this.loadBalancerClient = loadBalancerClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterPropertiesSet() throws InterruptedException {
        if (userService.findAll().isEmpty()) {

            while (true) {
                try {
                    railwayHealthCheck();
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

            System.out.println("User microservice's DB is initialized.");
        }
    }

    private void railwayHealthCheck() {
        var uri = loadBalancerClient.choose("train-railway")
                .getUri()
                .toString();
        restTemplate.getForEntity(uri + "/api/event", Void.class);
    }
}