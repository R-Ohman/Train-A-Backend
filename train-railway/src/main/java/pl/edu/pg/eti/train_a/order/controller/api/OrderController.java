package pl.edu.pg.eti.train_a.order.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.order.entity.Order;

public interface OrderController {
    @PostMapping("/api/order")
    @ResponseStatus(HttpStatus.OK)
    void postOrder(@RequestBody Order order);

    @DeleteMapping("/api/order/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrder(@PathVariable int orderId);
}