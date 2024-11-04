package pl.edu.pg.eti.train_a.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.controller.api.StationController;
import pl.edu.pg.eti.train_a.dto.GetStationsResponse;
import pl.edu.pg.eti.train_a.function.StationToResponseFunction;
import pl.edu.pg.eti.train_a.service.station.StationServiceImpl;

@RestController
@Log
public class StationDefaultController implements StationController {
    private final StationServiceImpl stationService;
    private final StationToResponseFunction stationToResponse;

    @Autowired
    public StationDefaultController(StationServiceImpl stationService, StationToResponseFunction stationToResponseFunction) {
        this.stationService = stationService;
        this.stationToResponse = stationToResponseFunction;
    }

    @Override
    public GetStationsResponse getStations() {
        return stationToResponse.apply(stationService.findAll());
    }
}
