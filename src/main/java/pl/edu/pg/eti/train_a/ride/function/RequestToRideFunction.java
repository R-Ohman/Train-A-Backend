package pl.edu.pg.eti.train_a.ride.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.ride.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.carriage.service.api.CarriageService;
import pl.edu.pg.eti.train_a.route.service.api.RouteService;

import java.util.function.BiFunction;

@Component
public class RequestToRideFunction implements BiFunction<Integer, PostRideRequest, Ride> {
    private final RouteService routeService;

    private final CarriageService carriageService;

    @Autowired
    public RequestToRideFunction(
            RouteService routeService,
            CarriageService carriageService
    ) {
        this.routeService = routeService;
        this.carriageService = carriageService;
    }

    @Override
    public Ride apply(Integer routeId, PostRideRequest postRideRequest) {
        return Ride.autoBuilder()
                .route(routeService.findById(routeId))
                // TODO
                .build();
    }
}
