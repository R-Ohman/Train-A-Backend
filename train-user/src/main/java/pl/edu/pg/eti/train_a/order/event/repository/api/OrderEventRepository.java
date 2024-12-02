package pl.edu.pg.eti.train_a.order.event.repository.api;

import pl.edu.pg.eti.train_a.order.dto.PostOrderRequest;

public interface OrderEventRepository {
    void create(PostOrderRequest request);

    void delete(int orderId);
}
