package pl.edu.pg.eti.train_a.order.event.repository.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.order.dto.PostOrderRequest;
import pl.edu.pg.eti.train_a.order.event.repository.api.OrderEventRepository;

@Repository
public class OrderEventRestRepository implements OrderEventRepository {
    private final RestTemplate restTemplate;

    private final LoadBalancerClient loadBalancerClient;

    @Autowired
    public OrderEventRestRepository(RestTemplate restTemplate, LoadBalancerClient loadBalancerClient) {
        this.restTemplate = restTemplate;
        this.loadBalancerClient = loadBalancerClient;
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
        return loadBalancerClient.choose("train-railway")
                .getUri()
                .toString();
    }
}
