package pl.edu.pg.eti.train_a.ride.controller.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.ride.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.ride.dto.PutRideRequest;

import java.util.Map;

public interface RideController {
    @PostMapping("/api/route/{routeId}/ride")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    Map<String, Integer> postRide(@PathVariable int routeId, @RequestBody @Valid PostRideRequest request);

    @PutMapping("/api/route/{routeId}/ride/{rideId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Map<String, Integer> putRide(@PathVariable int routeId, @PathVariable int rideId, @RequestBody @Valid PutRideRequest request);

    @DeleteMapping("/api/route/{routeId}/ride/{rideId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRide(@PathVariable int routeId, @PathVariable int rideId);
}
