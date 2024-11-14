package pl.edu.pg.eti.train_a.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.dto.GetRouteInfoResponse;
import pl.edu.pg.eti.train_a.entity.Carriage;
import pl.edu.pg.eti.train_a.entity.Price;
import pl.edu.pg.eti.train_a.entity.Route;
import pl.edu.pg.eti.train_a.entity.Station;

import java.util.List;
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
                                .segments(ride.getSchedules().stream()
                                        .map(schedule -> {
                                            var prices = ride.getPrices().stream()
                                                    .filter(price -> price.getRailway().getId() == schedule.getRailway().getId())
                                                    .collect(Collectors.toMap(
                                                            price -> price.getCarriage().getType(),
                                                            Price::getPrice
                                                    ));
                                            return GetRouteInfoResponse.Schedule.Segment.builder()
                                                    .time(List.of(schedule.getDepartureTime(), schedule.getArrivalTime()))
                                                    .price(prices)
                                                    .build();
                                        })
                                        .toList())
                                .build())
                        .toList()
                )
                .build();
    }
}
