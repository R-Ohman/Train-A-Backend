package pl.edu.pg.eti.train_a.ride.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.ride.dto.GetRideResponse;
import pl.edu.pg.eti.train_a.ride.dto.GetSearchResponse;

import java.math.BigDecimal;

public interface SearchController {
    @GetMapping("api/search")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetSearchResponse getSearchInfo(
            @RequestParam BigDecimal fromLatitude,
            @RequestParam BigDecimal fromLongitude,
            @RequestParam BigDecimal toLatitude,
            @RequestParam BigDecimal toLongitude,
            @RequestParam(required = false) Long time
    );

    @GetMapping("api/search/{rideId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRideResponse getSearchInfoById(@PathVariable int rideId);
}
