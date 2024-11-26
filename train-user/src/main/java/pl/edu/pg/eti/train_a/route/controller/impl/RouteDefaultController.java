package pl.edu.pg.eti.train_a.route.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.route.service.api.RouteService;
import pl.edu.pg.eti.train_a.route.controller.api.RouteController;

@RestController
@Log
public class RouteDefaultController implements RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteDefaultController(RouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public void postRoute(Route route) {
        routeService.create(route);
    }

    @Override
    public void putRoute(Route route) {
        routeService.update(route);
    }

    @Override
    public void deleteRoute(int routeId) {
        routeService.delete(routeId);
    }
}
