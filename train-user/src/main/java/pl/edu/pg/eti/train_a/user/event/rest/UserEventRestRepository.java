package pl.edu.pg.eti.train_a.user.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.user.dto.SignInRequest;
import pl.edu.pg.eti.train_a.user.entity.User;
import pl.edu.pg.eti.train_a.user.event.api.UserEventRepository;

@Repository
public class UserEventRestRepository implements UserEventRepository {
    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;

    @Autowired
    public UserEventRestRepository(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public void create(User user) {
        restTemplate.postForEntity(getUri() + "/api/user", user, Void.class);
    }

    @Override
    public void signIn(SignInRequest request) {
        restTemplate.postForEntity(getUri() + "/api/signin", request, Void.class);
    }

    private String getUri() {
        return discoveryClient.getInstances("train-railway").stream()
                .findFirst()
                .orElseThrow()
                .getUri()
                .toString();
    }
}
