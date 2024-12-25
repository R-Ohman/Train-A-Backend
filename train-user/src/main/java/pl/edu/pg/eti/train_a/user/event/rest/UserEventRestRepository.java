package pl.edu.pg.eti.train_a.user.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.user.dto.SignInRequest;
import pl.edu.pg.eti.train_a.user.entity.User;
import pl.edu.pg.eti.train_a.user.event.api.UserEventRepository;

@Repository
public class UserEventRestRepository implements UserEventRepository {
    private final RestTemplate restTemplate;

    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public UserEventRestRepository(RestTemplate restTemplate, LoadBalancerClient loadBalancerClient) {
        this.restTemplate = restTemplate;
        this.loadBalancerClient = loadBalancerClient;
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
        return loadBalancerClient.choose("train-railway")
                .getUri()
                .toString();
    }
}
