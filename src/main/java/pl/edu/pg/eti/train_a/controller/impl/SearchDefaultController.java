package pl.edu.pg.eti.train_a.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.controller.api.SearchController;
import pl.edu.pg.eti.train_a.dto.GetRideResponse;
import pl.edu.pg.eti.train_a.function.RideToResponseFunction;
import pl.edu.pg.eti.train_a.service.ride.RideServiceImpl;

@RestController
@Log
public class SearchDefaultController implements SearchController {
    private final RideServiceImpl rideService;
    private final RideToResponseFunction rideToResponse;

    @Autowired
    public SearchDefaultController(
            RideServiceImpl rideService,
            RideToResponseFunction rideToResponseFunction
    ) {
        this.rideService = rideService;
        this.rideToResponse = rideToResponseFunction;
    }

    @Override
    public GetRideResponse getSearchInfoById(int rideId) {
        return rideToResponse.apply(rideService.findById(rideId));
    }
}
