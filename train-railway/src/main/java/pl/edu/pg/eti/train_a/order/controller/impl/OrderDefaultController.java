package pl.edu.pg.eti.train_a.order.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.order.controller.api.OrderController;
import pl.edu.pg.eti.train_a.order.entity.Order;
import pl.edu.pg.eti.train_a.order.service.api.OrderService;

@RestController
@Log
public class OrderDefaultController implements OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderDefaultController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void postOrder(Order order) {
        orderService.create(order);
    }

    @Override
    public void deleteOrder(int orderId) {
        orderService.findById(orderId)
                .ifPresent(o -> orderService.delete(orderId));
    }
}
