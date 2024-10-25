package pl.edu.pg.eti.train_a.service.order;

import pl.edu.pg.eti.train_a.entity.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<Order> findAll();

    Order findById(UUID id);

    void create(Order order);

    void delete(UUID orderId);
}
