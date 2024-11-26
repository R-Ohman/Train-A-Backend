package pl.edu.pg.eti.train_a.station.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.station.entity.Station;
import pl.edu.pg.eti.train_a.station.event.api.StationEventRepository;

@Repository
public class StationEventRestRepository implements StationEventRepository {
    private final RestTemplate restTemplate;

    @Autowired
    public StationEventRestRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void create(Station station) {
        restTemplate.postForEntity("/api/station", station, Void.class);
    }

    @Override
    public void delete(int stationId) {
        restTemplate.delete("/api/station/{id}", stationId);
    }
}
