package pl.edu.pg.eti.train_a.order.event.repository.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.edu.pg.eti.train_a.order.dto.PostOrderRequest;
import pl.edu.pg.eti.train_a.order.event.repository.api.OrderEventRepository;

@Repository
public class OrderEventRestRepository implements OrderEventRepository {
    private final RestTemplate restTemplate;

    @Autowired
    public OrderEventRestRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void create(PostOrderRequest request) {
        restTemplate.postForEntity("/api/order", request, Void.class);
    }

    @Override
    public void delete(int orderId) {
        restTemplate.delete("/api/order/" + orderId);
    }
}
