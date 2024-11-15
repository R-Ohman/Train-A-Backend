package pl.edu.pg.eti.train_a.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.dto.PostRouteRequest;
import pl.edu.pg.eti.train_a.entity.Route;
import pl.edu.pg.eti.train_a.service.carriage.CarriageService;
import pl.edu.pg.eti.train_a.service.station.StationService;

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
