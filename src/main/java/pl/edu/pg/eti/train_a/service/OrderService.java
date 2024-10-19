package pl.edu.pg.eti.train_a.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Order;
import pl.edu.pg.eti.train_a.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RideService rideService;

    public OrderService(OrderRepository orderRepository, RideService rideService) {
        this.orderRepository = orderRepository;
        this.rideService = rideService;
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        this.rideService.findAll();
        return orderRepository.findAll();
    }
}
