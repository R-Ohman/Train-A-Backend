package pl.edu.pg.eti.train_a.ride.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.carriage.service.api.CarriageService;
import pl.edu.pg.eti.train_a.ride.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.ride.dto.PutRideRequest;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.ride.entity.Segment;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;
import pl.edu.pg.eti.train_a.route.service.api.RouteService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class RequestToRideFunction implements BiFunction<Integer, PostRideRequest, Ride> {
    private final RouteService routeService;

    private final CarriageService carriageService;

    private final RideService rideService;

    @Autowired
    public RequestToRideFunction(
            RouteService routeService,
            CarriageService carriageService,
            RideService rideService
    ) {
        this.routeService = routeService;
        this.rideService = rideService;
        this.carriageService = carriageService;
    }

    @Override
    public Ride apply(Integer routeId, PostRideRequest postRideRequest) {
        return Ride.builder()
                .route(routeService.findById(routeId).orElseThrow())
                .segments(postRideRequest.getSegments().stream()
                        .map(segment -> Segment.builder()
                                .arrival(LocalDateTime.parse(segment.getTime().get(1)))
                                .departure(LocalDateTime.parse(segment.getTime().get(0)))
                                .prices(
                                        segment.getPrice().entrySet().stream()
                                                .collect(Collectors.toMap(
                                                        entry -> carriageService.findByType(entry.getKey()).orElseThrow(),
                                                        Map.Entry::getValue))
                                )
                                .build())
                        .toList())
                .build();
    }

    public Ride apply(Integer rideId, Integer routeId, PutRideRequest putRideRequest) {
        return Ride.builder()
                .id(rideId)
                .route(routeService.findById(routeId).orElseThrow())
                .segments(IntStream.range(0, rideService.findById(rideId).orElseThrow().getSegments().size())
                        .mapToObj(index -> {
                            var oldSegment = rideService.findById(rideId).orElseThrow().getSegments().get(index);
                            var newSegmentData = putRideRequest.getSegments().get(index);
                            return Segment.builder()
                                    .id(oldSegment.getId())
                                    .ride(oldSegment.getRide())
                                    .arrival(LocalDateTime.parse(newSegmentData.getTime().get(1)))
                                    .departure(LocalDateTime.parse(newSegmentData.getTime().get(0)))
                                    .prices(
                                            newSegmentData.getPrice().entrySet().stream()
                                                    .collect(Collectors.toMap(
                                                            entry -> carriageService.findByType(entry.getKey()).orElseThrow(),
                                                            Map.Entry::getValue))
                                    )
                                    .build();
                        })
                        .toList())
                .build();
    }
}






