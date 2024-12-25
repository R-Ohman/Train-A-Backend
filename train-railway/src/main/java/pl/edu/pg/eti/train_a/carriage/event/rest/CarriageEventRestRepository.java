package pl.edu.pg.eti.train_a.carriage.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.carriage.event.api.CarriageEventRepository;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import java.util.UUID;

@Repository
public class CarriageEventRestRepository implements CarriageEventRepository {
    private final RestTemplate restTemplate;

    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public CarriageEventRestRepository(RestTemplate restTemplate, LoadBalancerClient loadBalancerClient) {
        this.restTemplate = restTemplate;
        this.loadBalancerClient = loadBalancerClient;
    }

    @Override
    public void create(Carriage carriage) {
        restTemplate.postForEntity(getUri() + "/api/carriage", carriage, Void.class);
    }

    @Override
    public void update(Carriage carriage) {
        restTemplate.put(getUri() + "/api/carriage", carriage);
    }

    @Override
    public void delete(UUID code) {
        restTemplate.delete(getUri() + "/api/carriage/{id}", code);
    }

    private String getUri() {
        return loadBalancerClient.choose("train-user")
                .getUri()
                .toString();
    }
}
