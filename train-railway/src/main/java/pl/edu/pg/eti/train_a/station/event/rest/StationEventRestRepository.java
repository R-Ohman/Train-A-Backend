package pl.edu.pg.eti.train_a.station.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.station.entity.Station;
import pl.edu.pg.eti.train_a.station.event.api.StationEventRepository;

@Repository
public class StationEventRestRepository implements StationEventRepository {
    private final RestTemplate restTemplate;
    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public StationEventRestRepository(RestTemplate restTemplate, LoadBalancerClient loadBalancerClient) {
        this.restTemplate = restTemplate;
        this.loadBalancerClient = loadBalancerClient;
    }

    @Override
    public void create(Station station) {
        restTemplate.postForEntity(getUri() + "/api/event/station", station, Void.class);
    }

    @Override
    public void delete(int stationId) {
        restTemplate.delete(getUri() + "/api/event/station/{id}", stationId);
    }

    private String getUri() {
        return loadBalancerClient.choose("train-user")
                .getUri()
                .toString();
    }
}
