package pl.edu.pg.eti.train_a.user.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.user.dto.SignInRequest;
import pl.edu.pg.eti.train_a.user.entity.User;
import pl.edu.pg.eti.train_a.user.event.api.UserEventRepository;

@Repository
public class UserEventRestRepository implements UserEventRepository {
    private final RestTemplate restTemplate;

    @Autowired
    public UserEventRestRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void create(User user) {
        restTemplate.postForEntity("/api/user", user, Void.class);
    }

    @Override
    public void signIn(SignInRequest request) {
        restTemplate.postForEntity("/api/signin", request, Void.class);
    }
}
