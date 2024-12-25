package pl.edu.pg.eti.train_a.order.event.repository.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.order.dto.PostOrderRequest;
import pl.edu.pg.eti.train_a.order.event.repository.api.OrderEventRepository;

@Repository
public class OrderEventRestRepository implements OrderEventRepository {
    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;

    @Autowired
    public OrderEventRestRepository(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public void create(PostOrderRequest request) {
        restTemplate.postForEntity(getUri() + "/api/order", request, Void.class);
    }

    @Override
    public void delete(int orderId) {
        restTemplate.delete(getUri() + "/api/order/" + orderId);
    }

    private String getUri() {
        return discoveryClient.getInstances("train-railway").stream()
                .findFirst()
                .orElseThrow()
                .getUri()
                .toString();
    }
}
