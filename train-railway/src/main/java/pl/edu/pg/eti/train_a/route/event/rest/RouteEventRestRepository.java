package pl.edu.pg.eti.train_a.route.event.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.route.event.api.RouteEventRepository;

@Repository
public class RouteEventRestRepository implements RouteEventRepository {
    private final RestTemplate restTemplate;

    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public RouteEventRestRepository(RestTemplate restTemplate, LoadBalancerClient loadBalancerClient) {
        this.restTemplate = restTemplate;
        this.loadBalancerClient = loadBalancerClient;
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
        return loadBalancerClient.choose("train-user")
                .getUri()
                .toString();
    }
}
