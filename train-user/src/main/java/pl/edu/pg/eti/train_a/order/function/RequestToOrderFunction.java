package pl.edu.pg.eti.train_a.order.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.order.dto.PostOrderRequest;
import pl.edu.pg.eti.train_a.order.entity.Order;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;
import pl.edu.pg.eti.train_a.station.service.api.StationService;
import pl.edu.pg.eti.train_a.user.service.api.UserService;

import java.util.function.Function;

@Component
public class RequestToOrderFunction implements Function<PostOrderRequest, Order> {
    private final RideService rideService;
    private final UserService userService;
    private final StationService stationService;

    @Autowired
    public RequestToOrderFunction(
            RideService rideService,
            StationService stationService,
            UserService userService
    ) {
        this.rideService = rideService;
        this.stationService = stationService;
        this.userService = userService;
    }

    @Override
    public Order apply(PostOrderRequest postOrderRequest) {
        var user = userService.getCurrentUser().orElseThrow();
        return Order.builder()
                .user(user)
                .ride(rideService.findById(postOrderRequest.getRideId()).orElseThrow())
                .seatId(postOrderRequest.getSeatId())
                .stationStart(stationService.findById(postOrderRequest.getStationStart()).orElseThrow())
                .stationEnd(stationService.findById(postOrderRequest.getStationEnd()).orElseThrow())
                .build();
    }
}
