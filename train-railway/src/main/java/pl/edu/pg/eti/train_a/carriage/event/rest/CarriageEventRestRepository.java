package pl.edu.pg.eti.train_a.carriage.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.carriage.event.api.CarriageEventRepository;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;

import java.util.UUID;

@Repository
public class CarriageEventRestRepository implements CarriageEventRepository {
    private final RestTemplate restTemplate;

    @Autowired
    public CarriageEventRestRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void create(Carriage carriage) {
        restTemplate.postForEntity("/api/carriage", carriage, Void.class);
    }

    @Override
    public void update(Carriage carriage) {
        restTemplate.put("/api/carriage", carriage);
    }

    @Override
    public void delete(UUID code) {
        restTemplate.delete("/api/carriage/{id}", code);
    }
}
