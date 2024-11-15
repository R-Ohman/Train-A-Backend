package pl.edu.pg.eti.train_a.order.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pg.eti.train_a.order.controller.api.OrderController;
import pl.edu.pg.eti.train_a.order.dto.GetOrdersResponse;
import pl.edu.pg.eti.train_a.order.dto.PostOrderRequest;
import pl.edu.pg.eti.train_a.order.function.OrderToResponseFunction;
import pl.edu.pg.eti.train_a.order.function.RequestToOrderFunction;
import pl.edu.pg.eti.train_a.order.service.api.OrderService;

import java.util.Map;

@RestController
@Log
public class OrderDefaultController implements OrderController {
    private final OrderService orderService;

    private final OrderToResponseFunction orderToResponse;

    private final RequestToOrderFunction requestToOrder;

    @Autowired
    public OrderDefaultController(
            OrderService orderService,
            OrderToResponseFunction orderToResponse,
            RequestToOrderFunction requestToOrder
    ) {
        this.orderService = orderService;
        this.orderToResponse = orderToResponse;
        this.requestToOrder = requestToOrder;
    }

    @Override
    public GetOrdersResponse getOrders() {
        return orderToResponse.apply(orderService.findAll());
    }

    @Override
    public Map<String, Integer> postOrder(PostOrderRequest request) {
        int orderId = orderService.create(requestToOrder.apply(request));
        return Map.of("orderId", orderId);
    }

    @Override
    public void deleteOrder(int orderId) {
        orderService.findById(orderId)
                .ifPresentOrElse(
                        character -> orderService.delete(orderId),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
