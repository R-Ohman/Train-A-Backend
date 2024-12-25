package pl.edu.pg.eti.train_a.ride.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.ride.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.ride.dto.PutRideRequest;
import pl.edu.pg.eti.train_a.ride.event.api.RideEventRepository;

@Repository
public class RideEventRestRepository implements RideEventRepository {
    private final RestTemplate restTemplate;

    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public RideEventRestRepository(RestTemplate restTemplate, LoadBalancerClient loadBalancerClient) {
        this.restTemplate = restTemplate;
        this.loadBalancerClient = loadBalancerClient;
    }

    @Override
    public void create(int routeId, PostRideRequest request) {
        restTemplate.postForEntity(getUri() + "/api/route/" + routeId + "/ride", request, Void.class);
    }

    @Override
    public void update(int routeId, int rideId, PutRideRequest request) {
        restTemplate.put(getUri() + "/api/route/" + routeId + "/ride/" + rideId, request);
    }

    @Override
    public void delete(int rideId) {
        restTemplate.delete(getUri() + "/api/ride/{id}", rideId);
    }

    private String getUri() {
        return loadBalancerClient.choose("train-user")
                .getUri()
                .toString();
    }
}
