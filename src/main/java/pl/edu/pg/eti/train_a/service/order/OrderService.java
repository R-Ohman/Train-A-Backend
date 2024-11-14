package pl.edu.pg.eti.train_a.service.order;

import pl.edu.pg.eti.train_a.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    Order findById(int id);

    void create(Order order);

    void delete(int orderId);
}
