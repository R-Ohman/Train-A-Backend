package pl.edu.pg.eti.train_a.route.controller.impl;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.pg.eti.train_a.route.controller.api.RouteController;
import pl.edu.pg.eti.train_a.route.dto.GetRouteInfoResponse;
import pl.edu.pg.eti.train_a.route.dto.GetRoutesResponse;
import pl.edu.pg.eti.train_a.route.dto.PostRouteRequest;
import pl.edu.pg.eti.train_a.route.dto.PutRouteRequest;
import pl.edu.pg.eti.train_a.route.function.RequestToRouteFunction;
import pl.edu.pg.eti.train_a.route.function.RouteInfoToResponseFunction;
import pl.edu.pg.eti.train_a.route.function.RouteToResponseFunction;
import pl.edu.pg.eti.train_a.route.service.api.RouteService;

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
        return routeInfoToResponse.apply(routeService.findById(id).orElseThrow());
    }

    @Override
    public Map<String, Integer> postRoute(PostRouteRequest request) {
        int newRouteId = routeService.create(requestToRoute.apply(request));
        return Map.of("id", newRouteId);
    }

    @Override
    public Map<String, Integer> putRoute(int id, PutRouteRequest request) {
        int newRouteId = routeService.update(requestToRoute.apply(id, request));
        return Map.of("id", newRouteId);
    }

    @Override
    public void deleteRoute(int id) {
        routeService.findById(id)
                .ifPresentOrElse(
                        character -> routeService.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
