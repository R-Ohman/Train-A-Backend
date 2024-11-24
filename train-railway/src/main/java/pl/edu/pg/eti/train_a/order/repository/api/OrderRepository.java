package pl.edu.pg.eti.train_a.order.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.train_a.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
