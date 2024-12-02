package pl.edu.pg.eti.train_a.order.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.exception.CustomResponseStatusException;
import pl.edu.pg.eti.train_a.order.controller.api.OrderController;
import pl.edu.pg.eti.train_a.order.dto.GetOrdersResponse;
import pl.edu.pg.eti.train_a.order.dto.PostOrderRequest;
import pl.edu.pg.eti.train_a.order.entity.OrderStatus;
import pl.edu.pg.eti.train_a.order.event.repository.api.OrderEventRepository;
import pl.edu.pg.eti.train_a.order.event.repository.rest.OrderEventRestRepository;
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
    private final OrderEventRepository orderEventRepository;

    @Autowired
    public OrderDefaultController(
            OrderService orderService,
            OrderToResponseFunction orderToResponse,
            RequestToOrderFunction requestToOrder,
            OrderEventRestRepository orderEventRepository,
            UserService userService) {
        this.orderService = orderService;
        this.orderToResponse = orderToResponse;
        this.requestToOrder = requestToOrder;
        this.userService = userService;
        this.orderEventRepository = orderEventRepository;
    }

    @Override
    public GetOrdersResponse getOrders(boolean all) {
        var user = userService.getCurrentUser().orElseThrow();
        if (all && user.getRole() != UserRole.MANAGER) {
            throw new CustomResponseStatusException(HttpStatus.UNAUTHORIZED, "invalidAccessToken", "Access is not granted");
        } else if (all) {
            return orderToResponse.apply(orderService.findAll());
        }
        return orderToResponse.apply(user.getOrders());
    }

    @Override
    public Map<String, Integer> postOrder(PostOrderRequest request) {
        int orderId = orderService.create(requestToOrder.apply(request));
        orderEventRepository.create(request);
        return Map.of("orderId", orderId);
    }

    @Override
    public void deleteOrder(int orderId) {
        var user = userService.getCurrentUser().orElseThrow();
        if (user.getRole() != UserRole.MANAGER
                && user.getOrders().stream().noneMatch(order -> order.getId() == orderId)) {
            throw new CustomResponseStatusException(HttpStatus.UNAUTHORIZED, "invalidAccessToken", "Access is not granted");
        }

        orderService.findById(orderId)
                .ifPresentOrElse(
                        o -> {
                            if (o.getStatus() != OrderStatus.ACTIVE) {
                                throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "orderNotActive", "Order is not active");
                            }
                            orderService.delete(orderId);
                        },
                        () -> {
                            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "orderNotFound", "Order not found");
                        }
                );
    }
}
