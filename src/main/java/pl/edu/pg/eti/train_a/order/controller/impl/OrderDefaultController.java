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
import pl.edu.pg.eti.train_a.user.entity.UserRole;
import pl.edu.pg.eti.train_a.user.service.api.UserService;

import java.util.Map;

@RestController
@Log
public class OrderDefaultController implements OrderController {
    private final OrderService orderService;

    private final OrderToResponseFunction orderToResponse;

    private final RequestToOrderFunction requestToOrder;
    private final UserService userService;

    @Autowired
    public OrderDefaultController(
            OrderService orderService,
            OrderToResponseFunction orderToResponse,
            RequestToOrderFunction requestToOrder,
            UserService userService) {
        this.orderService = orderService;
        this.orderToResponse = orderToResponse;
        this.requestToOrder = requestToOrder;
        this.userService = userService;
    }

    @Override
    public GetOrdersResponse getOrders(boolean all) {
        var user = userService.getCurrentUser().orElseThrow();
        if (all && user.getRole() != UserRole.MANAGER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } else if (all) {
            return orderToResponse.apply(orderService.findAll());
        }
        return orderToResponse.apply(user.getOrders());
    }

    @Override
    public Map<String, Integer> postOrder(PostOrderRequest request) {
        int orderId = orderService.create(requestToOrder.apply(request));
        return Map.of("orderId", orderId);
    }

    @Override
    public void deleteOrder(int orderId) {
        var user = userService.getCurrentUser().orElseThrow();
        if (user.getRole() != UserRole.MANAGER
                && user.getOrders().stream().noneMatch(order -> order.getId() == orderId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        orderService.findById(orderId)
                .ifPresentOrElse(
                        character -> orderService.delete(orderId),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
