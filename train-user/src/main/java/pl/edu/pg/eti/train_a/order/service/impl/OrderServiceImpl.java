package pl.edu.pg.eti.train_a.order.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.order.entity.Order;
import pl.edu.pg.eti.train_a.order.event.repository.api.OrderEventRepository;
import pl.edu.pg.eti.train_a.order.repository.api.OrderRepository;
import pl.edu.pg.eti.train_a.order.service.api.OrderService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventRepository orderEventRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderEventRepository orderEventRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderEventRepository = orderEventRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    public int create(Order order) {
        var newOrder = this.orderRepository.save(order);
//        this.orderEventRepository.create(newOrder);
        return newOrder.getId();
    }

    public void delete(int orderId) {
        var order = this.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        orderRepository.delete(order);
        orderEventRepository.delete(orderId);
    }
}
