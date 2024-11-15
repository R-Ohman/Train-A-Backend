package pl.edu.pg.eti.train_a.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.dto.GetStationsResponse;
import pl.edu.pg.eti.train_a.dto.PostStationRequest;

import java.util.Map;

public interface StationController {
    @GetMapping("api/station")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetStationsResponse getStations();

    @PostMapping("api/station")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Map<String, Integer> postStation(@RequestBody PostStationRequest request);
}
