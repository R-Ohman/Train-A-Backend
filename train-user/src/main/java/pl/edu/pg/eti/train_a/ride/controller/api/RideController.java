package pl.edu.pg.eti.train_a.ride.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.ride.entity.Ride;

public interface RideController {
    @PostMapping("/api/ride")
    @ResponseStatus(HttpStatus.CREATED)
    void postRide(@RequestBody Ride ride);

    @PutMapping("/api/ride")
    @ResponseStatus(HttpStatus.CREATED)
    void putRide(@RequestBody Ride ride);

    @DeleteMapping("/api/ride/{rideId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRide(@PathVariable int rideId);
}
