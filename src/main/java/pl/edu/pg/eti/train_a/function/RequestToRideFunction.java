package pl.edu.pg.eti.train_a.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.entity.Ride;
import pl.edu.pg.eti.train_a.service.carriage.CarriageService;
import pl.edu.pg.eti.train_a.service.route.RouteService;

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
