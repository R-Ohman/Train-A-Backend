package pl.edu.pg.eti.train_a.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Order;
import pl.edu.pg.eti.train_a.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public void create(Order order) {
        this.orderRepository.save(order);
    }

    @Transactional
    public void delete(UUID orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        order.getUser().getOrders().remove(order);
        order.getRide().getOrders().remove(order);
        orderRepository.delete(order);
    }
}
