package pl.edu.pg.eti.train_a.ride.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.ride.event.api.RideEventRepository;

@Repository
public class RideEventRestRepository implements RideEventRepository {
    private final RestTemplate restTemplate;

    @Autowired
    public RideEventRestRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void create(Ride ride) {
        restTemplate.postForEntity("/api/ride", ride, Void.class);
    }

    @Override
    public void update(Ride ride) {
        restTemplate.put("/api/ride", ride);
    }

    @Override
    public void delete(int rideId) {
        restTemplate.delete("/api/ride/{id}", rideId);
    }
}
