package pl.edu.pg.eti.train_a.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.controller.api.OrderController;
import pl.edu.pg.eti.train_a.dto.GetOrdersResponse;
import pl.edu.pg.eti.train_a.function.OrderToResponseFunction;
import pl.edu.pg.eti.train_a.service.order.OrderServiceImpl;

@RestController
@Log
public class OrderDefaultController implements OrderController {
    private final OrderServiceImpl orderService;
    private final OrderToResponseFunction orderToResponse;

    @Autowired
    public OrderDefaultController(OrderServiceImpl orderService, OrderToResponseFunction orderToResponse) {
        this.orderService = orderService;
        this.orderToResponse = orderToResponse;
    }

    @Override
    public GetOrdersResponse getOrders() {
        return orderToResponse.apply(orderService.findAll());
    }
}
