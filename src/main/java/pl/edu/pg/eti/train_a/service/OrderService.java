package pl.edu.pg.eti.train_a.service;

import org.springframework.stereotype.Service;
import pl.edu.pg.eti.train_a.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
