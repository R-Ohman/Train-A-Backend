package pl.edu.pg.eti.train_a.controller.impl;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.controller.api.RouteController;
import pl.edu.pg.eti.train_a.dto.GetRouteInfoResponse;
import pl.edu.pg.eti.train_a.dto.GetRoutesResponse;
import pl.edu.pg.eti.train_a.dto.PostRouteRequest;
import pl.edu.pg.eti.train_a.function.RequestToRouteFunction;
import pl.edu.pg.eti.train_a.function.RouteInfoToResponseFunction;
import pl.edu.pg.eti.train_a.function.RouteToResponseFunction;
import pl.edu.pg.eti.train_a.service.route.RouteService;

import java.util.Map;

@RestController
@Log
public class RouteDefaultController implements RouteController {
    private final RouteService routeService;

    private final RouteInfoToResponseFunction routeInfoToResponse;

    private final RouteToResponseFunction routeToResponse;

    private final RequestToRouteFunction requestToRoute;

    @Autowired
    public RouteDefaultController(
            RouteService routeService,
            RouteToResponseFunction routeToResponseFunction,
            RouteInfoToResponseFunction routeInfoToResponseFunction,
            RequestToRouteFunction requestToRoute
    ) {
        this.routeService = routeService;
        this.routeToResponse = routeToResponseFunction;
        this.routeInfoToResponse = routeInfoToResponseFunction;
        this.requestToRoute = requestToRoute;
    }

    @Override
    public GetRoutesResponse getRoutes() {
        return routeToResponse.apply(routeService.findAll());
    }

    @Override
    public GetRouteInfoResponse getRouteInfoById(int id) {
        return routeInfoToResponse.apply(routeService.findById(id));
    }

    @Override
    public Map<String, Integer> postRoute(PostRouteRequest request) {
        int newRouteId = routeService.create(requestToRoute.apply(request));
        return Map.of("id", newRouteId);
    }
}
