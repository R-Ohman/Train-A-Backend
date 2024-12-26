package pl.edu.pg.eti.train_a.station.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.station.entity.Station;

public interface StationController {
    @PostMapping("/api/event/station")
    @ResponseStatus(HttpStatus.CREATED)
    void postStation(@RequestBody Station station);

    @DeleteMapping("/api/event/station/{stationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteStation(@PathVariable int stationId);
}
