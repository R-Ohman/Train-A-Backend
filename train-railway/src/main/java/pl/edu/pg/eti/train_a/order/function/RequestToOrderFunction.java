package pl.edu.pg.eti.train_a.order.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.order.dto.PostOrderRequest;
import pl.edu.pg.eti.train_a.order.entity.Order;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;

import java.util.function.Function;

@Component
public class RequestToOrderFunction implements Function<PostOrderRequest, Order> {
    private final RideService rideService;

    @Autowired
    public RequestToOrderFunction(RideService rideService) {
        this.rideService = rideService;
    }

    @Override
    public Order apply(PostOrderRequest postOrderRequest) {
        return Order.builder()
                .ride(rideService.findById(postOrderRequest.getRideId()).orElseThrow())
                .seatId(postOrderRequest.getSeatId())
                .build();
    }
}
