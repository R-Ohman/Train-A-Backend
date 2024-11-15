package pl.edu.pg.eti.train_a.ride.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pg.eti.train_a.ride.controller.api.SearchController;
import pl.edu.pg.eti.train_a.ride.dto.GetRideResponse;
import pl.edu.pg.eti.train_a.ride.dto.GetSearchResponse;
import pl.edu.pg.eti.train_a.ride.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.ride.function.RequestToRideFunction;
import pl.edu.pg.eti.train_a.ride.function.RideToResponseFunction;
import pl.edu.pg.eti.train_a.ride.function.SearchToResponseFunction;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;
import pl.edu.pg.eti.train_a.route.service.api.RouteService;
import pl.edu.pg.eti.train_a.station.service.api.StationService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
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
    private final RequestToRideFunction requestToRide;

    @Autowired
    public SearchDefaultController(
            RideService rideService,
            RouteService routeService,
            StationService stationService,
            RideToResponseFunction rideToResponseFunction,
            SearchToResponseFunction searchToResponseFunction,
            RequestToRideFunction requestToRide
    ) {
        this.rideService = rideService;
        this.routeService = routeService;
        this.stationService = stationService;
        this.rideToResponse = rideToResponseFunction;
        this.searchToResponse = searchToResponseFunction;
        this.requestToRide = requestToRide;
    }

    @Override
    public GetSearchResponse getSearchInfo(
            BigDecimal fromLatitude, BigDecimal fromLongitude, BigDecimal toLatitude, BigDecimal toLongitude, Long time
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
        return rideToResponse.apply(rideService.findById(rideId).orElseThrow());
    }

    @Override
    public Map<String, Integer> postRide(int routeId, PostRideRequest request) {
        int newRideId = rideService.create(requestToRide.apply(routeId, request));
        return Map.of("id", newRideId);
    }

    @Override
    public void deleteRide(int routeId, int rideId) {
        rideService.findById(rideId)
                .ifPresentOrElse(
                        character -> rideService.delete(rideId),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
