package pl.edu.pg.eti.train_a.order.service.api;

import pl.edu.pg.eti.train_a.order.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    Order findById(int id);

    void create(Order order);

    void delete(int orderId);
}
