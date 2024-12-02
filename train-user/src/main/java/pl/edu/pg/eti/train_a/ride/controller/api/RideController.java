package pl.edu.pg.eti.train_a.ride.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.ride.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.ride.dto.PutRideRequest;

public interface RideController {
    @PostMapping("/api/route/{routeId}/ride")
    @ResponseStatus(HttpStatus.CREATED)
    void postRide(@PathVariable int routeId, @RequestBody PostRideRequest request);

    @PutMapping("/api/route/{routeId}/ride/{rideId}")
    @ResponseStatus(HttpStatus.CREATED)
    void putRide(@PathVariable int routeId, @PathVariable int rideId, @RequestBody PutRideRequest request);

    @DeleteMapping("/api/ride/{rideId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRide(@PathVariable int rideId);
}
