package pl.edu.pg.eti.train_a.route.controller.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.route.dto.GetRouteInfoResponse;
import pl.edu.pg.eti.train_a.route.dto.GetRoutesResponse;
import pl.edu.pg.eti.train_a.route.dto.PostRouteRequest;
import pl.edu.pg.eti.train_a.route.dto.PutRouteRequest;

import java.util.Map;

public interface RouteController {
    @GetMapping("api/route")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRoutesResponse getRoutes();

    @GetMapping("api/route/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRouteInfoResponse getRouteInfoById(@PathVariable int id);

    @PostMapping("api/route")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Map<String, Integer> postRoute(@RequestBody @Valid PostRouteRequest request);

    @PutMapping("/api/route/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Map<String, Integer> putRoute(@PathVariable int id, @RequestBody @Valid PutRouteRequest request);

    @DeleteMapping("/api/route/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRoute(@PathVariable int id);
}

























