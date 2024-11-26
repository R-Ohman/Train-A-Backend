package pl.edu.pg.eti.train_a.route.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.route.event.api.RouteEventRepository;

@Repository
public class RouteEventRestRepository implements RouteEventRepository {
    private final RestTemplate restTemplate;

    @Autowired
    public RouteEventRestRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void create(Route route) {
        restTemplate.postForEntity("/api/route", route, Void.class);
    }

    @Override
    public void update(Route route) {
        restTemplate.put("/api/route", route);
    }

    @Override
    public void delete(int routeId) {
        restTemplate.delete("/api/route/{id}", routeId);
    }
}
