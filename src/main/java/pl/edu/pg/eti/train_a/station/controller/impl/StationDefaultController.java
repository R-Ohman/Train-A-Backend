package pl.edu.pg.eti.train_a.station.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.station.controller.api.StationController;
import pl.edu.pg.eti.train_a.station.dto.GetStationsResponse;
import pl.edu.pg.eti.train_a.station.dto.PostStationRequest;
import pl.edu.pg.eti.train_a.station.function.RequestToStationFunction;
import pl.edu.pg.eti.train_a.station.function.StationToResponseFunction;
import pl.edu.pg.eti.train_a.station.service.api.StationService;

import java.util.Map;

@RestController
@Log
public class StationDefaultController implements StationController {
    private final StationService stationService;
    private final StationToResponseFunction stationToResponse;
    private final RequestToStationFunction requestToStation;

    @Autowired
    public StationDefaultController(
            StationService stationService,
            StationToResponseFunction stationToResponseFunction,
            RequestToStationFunction requestToStation
    ) {
        this.stationService = stationService;
        this.stationToResponse = stationToResponseFunction;
        this.requestToStation = requestToStation;
    }

    @Override
    public GetStationsResponse getStations() {
        return stationToResponse.apply(stationService.findAll());
    }

    @Override
    public Map<String, Integer> postStation(PostStationRequest request) {
        int newStationId = stationService.create(requestToStation.apply(request));
        return Map.of("id", newStationId);
    }
}
