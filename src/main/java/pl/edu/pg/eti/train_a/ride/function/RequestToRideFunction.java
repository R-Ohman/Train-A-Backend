package pl.edu.pg.eti.train_a.ride.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.carriage.service.api.CarriageService;
import pl.edu.pg.eti.train_a.ride.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.ride.entity.Segment;
import pl.edu.pg.eti.train_a.route.service.api.RouteService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

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
                .segments(postRideRequest.getSegments().stream()
                        .map(segment -> Segment.builder()
                                .arrival(LocalDateTime.parse(segment.getTime().get(1)))
                                .departure(LocalDateTime.parse(segment.getTime().get(0)))
                                .prices(
                                        segment.getPrice().entrySet().stream()
                                                .collect(Collectors.toMap(
                                                        entry -> carriageService.findByType(entry.getKey()),
                                                        Map.Entry::getValue))
                                )
                                .build())
                        .toList())
                .build();
    }
}
