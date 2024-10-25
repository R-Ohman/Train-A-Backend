package pl.edu.pg.eti.train_a.service.order;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Order;
import pl.edu.pg.eti.train_a.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

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

    public Order findById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    public void create(Order order) {
        this.orderRepository.save(order);
    }

    public void delete(UUID orderId) {
        var order = this.findById(orderId);
        order.getUser().getOrders().remove(order);
        order.getRide().getOrders().remove(order);
        orderRepository.delete(order);
    }
}
