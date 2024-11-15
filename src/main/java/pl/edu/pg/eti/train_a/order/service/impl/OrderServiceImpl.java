package pl.edu.pg.eti.train_a.order.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.order.entity.Order;
import pl.edu.pg.eti.train_a.order.repository.api.OrderRepository;
import pl.edu.pg.eti.train_a.order.service.api.OrderService;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    public void create(Order order) {
        this.orderRepository.save(order);
    }

    public void delete(int orderId) {
        var order = this.findById(orderId);
        order.getUser().getOrders().remove(order);
        order.getRide().getOrders().remove(order);
        orderRepository.delete(order);
    }
}
