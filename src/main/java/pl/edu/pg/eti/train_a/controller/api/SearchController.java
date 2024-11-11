package pl.edu.pg.eti.train_a.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.pg.eti.train_a.dto.GetRideResponse;

public interface SearchController {
    @GetMapping("api/search/{rideId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRideResponse getSearchInfoById(@PathVariable int rideId);
}
