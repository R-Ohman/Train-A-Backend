package pl.edu.pg.eti.train_a.order.controller.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.order.dto.GetOrdersResponse;
import pl.edu.pg.eti.train_a.order.dto.PostOrderRequest;

import java.util.Map;

public interface OrderController {
    @GetMapping("api/order")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetOrdersResponse getOrders(@RequestParam(required = false) boolean all);

    @PostMapping("/api/order")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    Map<String, Integer> postOrder(@RequestBody @Valid PostOrderRequest request);

    @DeleteMapping("/api/order/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrder(@PathVariable int orderId);
}
