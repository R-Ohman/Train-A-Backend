package pl.edu.pg.eti.train_a.ride.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.exception.CustomResponseStatusException;
import pl.edu.pg.eti.train_a.ride.controller.api.RideController;
import pl.edu.pg.eti.train_a.ride.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.ride.dto.PutRideRequest;
import pl.edu.pg.eti.train_a.ride.function.RequestToRideFunction;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;

import java.util.Map;

@RestController
@Log
public class RideDefaultController implements RideController {
    private final RequestToRideFunction requestToRide;
    private final RideService rideService;

    @Autowired
    public RideDefaultController(RequestToRideFunction requestToRide, RideService rideService) {
        this.requestToRide = requestToRide;
        this.rideService = rideService;
    }

    @Override
    public Map<String, Integer> postRide(int routeId, PostRideRequest request) {
        try {
            int newRideId = rideService.create(requestToRide.apply(routeId, request));
            return Map.of("id", newRideId);
        } catch (Exception e) {
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "routeNotFound", "Route not found");
        }
    }

    @Override
    public Map<String, Integer> putRide(int routeId, int rideId, PutRideRequest request) {
        try {
            var ride = requestToRide.apply(rideId, routeId, request);
            int updatedRideId = rideService.update(ride);
            return Map.of("id", updatedRideId);
        } catch (Exception e) {
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "rideNotFound", "Ride not found");
        }
    }

    @Override
    public void deleteRide(int routeId, int rideId) {
        rideService.findById(rideId)
                .ifPresentOrElse(
                        character -> rideService.delete(rideId),
                        () -> {
                            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "rideNotFound", "Ride not found");
                        }
                );
    }
}
