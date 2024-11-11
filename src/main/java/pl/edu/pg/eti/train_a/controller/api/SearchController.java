package pl.edu.pg.eti.train_a.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.dto.GetRideResponse;
import pl.edu.pg.eti.train_a.dto.GetSearchResponse;

public interface SearchController {
    @GetMapping("api/search")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetSearchResponse getSearchInfo(
            @RequestParam double fromLatitude,
            @RequestParam double fromLongitude,
            @RequestParam double toLatitude,
            @RequestParam double toLongitude,
            @RequestParam(required = false) Long time
    );

    @GetMapping("api/search/{rideId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRideResponse getSearchInfoById(@PathVariable int rideId);
}
