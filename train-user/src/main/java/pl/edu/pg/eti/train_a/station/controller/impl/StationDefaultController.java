package pl.edu.pg.eti.train_a.station.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.station.controller.api.StationController;
import pl.edu.pg.eti.train_a.station.entity.Station;
import pl.edu.pg.eti.train_a.station.service.api.StationService;

@RestController
@Log
public class StationDefaultController implements StationController {
    private final StationService stationService;

    @Autowired
    public StationDefaultController(StationService stationService) {
        this.stationService = stationService;
    }

    @Override
    public void postStation(Station station) {
        stationService.create(station);
    }

    @Override
    public void deleteStation(int stationId) {
        stationService.delete(stationId);
    }
}
