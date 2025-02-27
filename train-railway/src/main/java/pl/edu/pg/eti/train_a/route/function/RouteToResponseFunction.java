package pl.edu.pg.eti.train_a.route.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.route.dto.GetRoutesResponse;
import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;
import pl.edu.pg.eti.train_a.station.entity.Station;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RouteToResponseFunction implements Function<List<Route>, GetRoutesResponse> {

    @Override
    public GetRoutesResponse apply(List<Route> routes) {
        return GetRoutesResponse.builder()
                .routes(routes.stream()
                        .map(route -> GetRoutesResponse.Route.builder()
                                .id(route.getId())
                                .path(
                                        route.getStations().stream()
                                                .map(Station::getId)
                                                .collect(Collectors.toList())
                                )
                                .carriages(
                                        route.getCarriages().stream()
                                                .map(Carriage::getType)
                                                .collect(Collectors.toList()
                                                )
                                )
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .build();
    }
}
