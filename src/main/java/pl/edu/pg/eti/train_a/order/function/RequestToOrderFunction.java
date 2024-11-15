package pl.edu.pg.eti.train_a.order.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.order.dto.PostOrderRequest;
import pl.edu.pg.eti.train_a.order.entity.Order;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;
import pl.edu.pg.eti.train_a.station.service.api.StationService;

import java.util.function.Function;

@Component
public class RequestToOrderFunction implements Function<PostOrderRequest, Order> {
    private final RideService rideService;

    private final StationService stationService;

    @Autowired
    public RequestToOrderFunction(RideService rideService, StationService stationService) {
        this.rideService = rideService;
        this.stationService = stationService;
    }

    @Override
    public Order apply(PostOrderRequest postOrderRequest) {
        // TODO: take into account the USER
        return Order.builder()
                .ride(rideService.findById(postOrderRequest.getRideId()))
                .seatId(postOrderRequest.getSeatId())
                .stationStart(stationService.findById(postOrderRequest.getStationStart()))
                .stationEnd(stationService.findById(postOrderRequest.getStationEnd()))
                .build();
    }
}
