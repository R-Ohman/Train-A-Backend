package pl.edu.pg.eti.train_a.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.controller.api.SearchController;
import pl.edu.pg.eti.train_a.dto.GetRideResponse;
import pl.edu.pg.eti.train_a.dto.GetSearchResponse;
import pl.edu.pg.eti.train_a.function.RideToResponseFunction;
import pl.edu.pg.eti.train_a.function.SearchToResponseFunction;
import pl.edu.pg.eti.train_a.service.ride.RideService;
import pl.edu.pg.eti.train_a.service.route.RouteService;
import pl.edu.pg.eti.train_a.service.station.StationService;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;

@RestController
@Log
public class SearchDefaultController implements SearchController {
    private final RideService rideService;
    private final RouteService routeService;
    private final StationService stationService;
    private final RideToResponseFunction rideToResponse;
    private final SearchToResponseFunction searchToResponse;

    @Autowired
    public SearchDefaultController(
            RideService rideService,
            RouteService routeService,
            StationService stationService,
            RideToResponseFunction rideToResponseFunction,
            SearchToResponseFunction searchToResponseFunction
    ) {
        this.rideService = rideService;
        this.routeService = routeService;
        this.stationService = stationService;
        this.rideToResponse = rideToResponseFunction;
        this.searchToResponse = searchToResponseFunction;
    }

    @Override
    public GetSearchResponse getSearchInfo(
            double fromLatitude, double fromLongitude, double toLatitude, double toLongitude, Long time
    ) {
        Optional<LocalDateTime> optionalTime = (time != null) ? Optional.of(LocalDateTime.ofEpochSecond(time, 0, UTC)) : Optional.empty();
        var from = stationService.findNearestStation(fromLatitude, fromLongitude);
        var to = stationService.findNearestStation(toLatitude, toLongitude);
        return searchToResponse.apply(
                from,
                to,
                routeService.findFromToStation(from, to),
                optionalTime
        );
    }

    @Override
    public GetRideResponse getSearchInfoById(int rideId) {
        return rideToResponse.apply(rideService.findById(rideId));
    }
}
