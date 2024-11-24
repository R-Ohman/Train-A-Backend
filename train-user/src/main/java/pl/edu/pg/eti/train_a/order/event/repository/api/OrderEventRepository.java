package pl.edu.pg.eti.train_a.order.event.repository.api;

import pl.edu.pg.eti.train_a.order.entity.Order;

public interface OrderEventRepository {
    void create(Order order);

    void delete(int orderId);
}
