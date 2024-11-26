package pl.edu.pg.eti.train_a.ride.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.ride.controller.api.RideController;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;

@RestController
@Log
public class RideDefaultController implements RideController {
    private final RideService rideService;

    @Autowired
    public RideDefaultController(RideService rideService) {
        this.rideService = rideService;
    }

    @Override
    public void postRide(Ride ride) {
        rideService.create(ride);
    }

    @Override
    public void putRide(Ride ride) {
        rideService.update(ride);
    }

    @Override
    public void deleteRide(int rideId) {
        rideService.delete(rideId);
    }
}
