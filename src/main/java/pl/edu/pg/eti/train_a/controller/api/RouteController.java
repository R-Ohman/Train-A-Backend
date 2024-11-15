package pl.edu.pg.eti.train_a.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.dto.GetRouteInfoResponse;
import pl.edu.pg.eti.train_a.dto.GetRoutesResponse;
import pl.edu.pg.eti.train_a.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.dto.PostRouteRequest;

import java.util.Map;

public interface RouteController {
    @GetMapping("api/routes")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRoutesResponse getRoutes();

    @GetMapping("api/routes/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRouteInfoResponse getRouteInfoById(@PathVariable int id);

    @PostMapping("api/route")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Map<String, Integer> postRoute(@RequestBody PostRouteRequest request);

    @PostMapping("/api/route/{routeId}/ride")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    Map<String, Integer> postRide(@PathVariable int routeId, @RequestBody PostRideRequest request);

}

























