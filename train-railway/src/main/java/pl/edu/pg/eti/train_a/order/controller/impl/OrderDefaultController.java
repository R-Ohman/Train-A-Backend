package pl.edu.pg.eti.train_a.order.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.order.controller.api.OrderController;
import pl.edu.pg.eti.train_a.order.dto.PostOrderRequest;
import pl.edu.pg.eti.train_a.order.function.RequestToOrderFunction;
import pl.edu.pg.eti.train_a.order.service.api.OrderService;

@RestController
@Log
public class OrderDefaultController implements OrderController {
    private final OrderService orderService;
    private final RequestToOrderFunction requestToOrderFunction;
    @Autowired
    public OrderDefaultController(OrderService orderService, RequestToOrderFunction requestToOrderFunction) {
        this.orderService = orderService;
        this.requestToOrderFunction = requestToOrderFunction;
    }

    @Override
    public void postOrder(PostOrderRequest request) {
        orderService.create(requestToOrderFunction.apply(request));
    }

    @Override
    public void deleteOrder(int orderId) {
        orderService.findById(orderId)
                .ifPresent(o -> orderService.delete(orderId));
    }
}
