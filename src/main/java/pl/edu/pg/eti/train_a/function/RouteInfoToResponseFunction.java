package pl.edu.pg.eti.train_a.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.dto.GetRouteInfoResponse;
import pl.edu.pg.eti.train_a.entity.Carriage;
import pl.edu.pg.eti.train_a.entity.Route;
import pl.edu.pg.eti.train_a.entity.Station;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RouteInfoToResponseFunction implements Function<Route, GetRouteInfoResponse> {
    @Override
    public GetRouteInfoResponse apply(Route route) {
        return GetRouteInfoResponse.builder()
                .id(route.getId())
                .path(route.getStations().stream()
                        .map(Station::getId)
                        .toList())
                .carriages(route.getCarriages().stream()
                        .map(Carriage::getType)
                        .toList())
                .schedule(route.getRides().stream()
                        .map(ride -> GetRouteInfoResponse.Schedule.builder()
                                .rideId(ride.getId())
                                .segments(ride.getSegments().stream()
                                        .map(segment -> GetRouteInfoResponse.Schedule.Segment.builder()
                                                .time(List.of(segment.getDeparture(), segment.getArrival()))
                                                .price(
                                                        segment.getPrices().entrySet().stream()
                                                                .collect(Collectors.toMap(
                                                                        entry -> entry.getKey().getType(),
                                                                        Map.Entry::getValue))
                                                )
                                                .build())
                                        .toList())
                                .build())
                        .toList()
                )
                .build();
    }
}
