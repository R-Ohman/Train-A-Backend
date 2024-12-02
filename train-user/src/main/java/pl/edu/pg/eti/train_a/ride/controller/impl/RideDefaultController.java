package pl.edu.pg.eti.train_a.ride.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.ride.controller.api.RideController;
import pl.edu.pg.eti.train_a.ride.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.ride.dto.PutRideRequest;
import pl.edu.pg.eti.train_a.ride.function.RequestToRideFunction;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;

@RestController
@Log
public class RideDefaultController implements RideController {
    private final RideService rideService;

    private final RequestToRideFunction requestToRideFunction;

    @Autowired
    public RideDefaultController(RideService rideService, RequestToRideFunction requestToRideFunction) {
        this.rideService = rideService;
        this.requestToRideFunction = requestToRideFunction;
    }

    @Override
    public void postRide(int routeId, PostRideRequest request) {
        rideService.create(requestToRideFunction.apply(routeId, request));
    }

    @Override
    public void putRide(int routeId, int rideId, PutRideRequest request) {
        rideService.update(requestToRideFunction.apply(rideId, routeId, request));
    }

    @Override
    public void deleteRide(int rideId) {
        rideService.delete(rideId);
    }
}
