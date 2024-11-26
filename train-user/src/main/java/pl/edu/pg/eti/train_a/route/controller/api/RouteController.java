package pl.edu.pg.eti.train_a.route.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.route.entity.Route;

public interface RouteController {
    @PostMapping("/api/route")
    @ResponseStatus(HttpStatus.CREATED)
    void postRoute(@RequestBody Route route);

    @PutMapping("/api/route")
    @ResponseStatus(HttpStatus.CREATED)
    void putRoute(@RequestBody Route route);

    @DeleteMapping("/api/route/{routeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRoute(@PathVariable int routeId);
}
