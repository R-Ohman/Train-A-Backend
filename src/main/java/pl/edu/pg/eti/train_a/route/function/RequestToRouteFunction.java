package pl.edu.pg.eti.train_a.route.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.route.dto.PostRouteRequest;
import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.carriage.service.api.CarriageService;
import pl.edu.pg.eti.train_a.station.service.api.StationService;

import java.util.function.Function;

@Component
public class RequestToRouteFunction implements Function<PostRouteRequest, Route> {

    private final StationService stationService;

    private final CarriageService carriageService;

    @Autowired
    public RequestToRouteFunction(StationService stationService, CarriageService carriageService) {
        this.stationService = stationService;
        this.carriageService = carriageService;
    }

    @Override
    public Route apply(PostRouteRequest postRouteRequest) {
        return Route.builder()
                .stations(postRouteRequest.getPath().stream()
                        .map(stationService::findById)
                        .toList())
                .carriages(postRouteRequest.getCarriages().stream()
                        .map(carriageService::findByType)
                        .toList())
                .build();
    }
}
