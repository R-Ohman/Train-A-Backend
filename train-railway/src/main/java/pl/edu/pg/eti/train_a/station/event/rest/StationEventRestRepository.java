package pl.edu.pg.eti.train_a.station.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.station.entity.Station;
import pl.edu.pg.eti.train_a.station.event.api.StationEventRepository;

@Repository
public class StationEventRestRepository implements StationEventRepository {
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    @Autowired
    public StationEventRestRepository(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public void create(Station station) {
        restTemplate.postForEntity(getUri() + "/api/station", station, Void.class);
    }

    @Override
    public void delete(int stationId) {
        restTemplate.delete(getUri() + "/api/station/{id}", stationId);
    }

    private String getUri() {
        return discoveryClient.getInstances("train-user").stream()
                .findFirst()
                .orElseThrow()
                .getUri()
                .toString();
    }
}
