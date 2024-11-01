package pl.edu.pg.eti.train_a.controller.impl;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.controller.api.RouteController;
import pl.edu.pg.eti.train_a.dto.GetRoutesResponse;
import pl.edu.pg.eti.train_a.function.RouteToResponseFunction;
import pl.edu.pg.eti.train_a.service.route.RouteServiceImpl;

@RestController
@Log
public class RouteDefaultController implements RouteController {
    private final RouteServiceImpl routeService;

    private final RouteToResponseFunction routeToResponse;

    @Autowired
    public RouteDefaultController(RouteServiceImpl routeService, RouteToResponseFunction routeToResponseFunction) {
        this.routeService = routeService;
        this.routeToResponse = routeToResponseFunction;
    }

    @Override
    public GetRoutesResponse getRoutes() {
        return routeToResponse.apply(routeService.findAll());
    }
}
