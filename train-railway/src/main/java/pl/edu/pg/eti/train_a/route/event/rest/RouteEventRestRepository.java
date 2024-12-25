package pl.edu.pg.eti.train_a.route.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.route.event.api.RouteEventRepository;

@Repository
public class RouteEventRestRepository implements RouteEventRepository {
    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;

    @Autowired
    public RouteEventRestRepository(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public void create(Route route) {
        restTemplate.postForEntity(getUri() + "/api/route", route, Void.class);
    }

    @Override
    public void update(Route route) {
        restTemplate.put(getUri() + "/api/route", route);
    }

    @Override
    public void delete(int routeId) {
        restTemplate.delete(getUri() + "/api/route/{id}", routeId);
    }

    private String getUri() {
        return discoveryClient.getInstances("train-user").stream()
                .findFirst()
                .orElseThrow()
                .getUri()
                .toString();
    }
}
