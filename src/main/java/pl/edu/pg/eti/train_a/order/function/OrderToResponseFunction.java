package pl.edu.pg.eti.train_a.order.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.order.dto.GetOrdersResponse;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;
import pl.edu.pg.eti.train_a.order.entity.Order;
import pl.edu.pg.eti.train_a.station.entity.Station;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrderToResponseFunction implements Function<List<Order>, GetOrdersResponse> {

    @Override
    public GetOrdersResponse apply(List<Order> orders) {
        return GetOrdersResponse.builder()
                .orders(orders.stream()
                        .map(order -> GetOrdersResponse.Order.builder()
                                .id(order.getId())
                                .rideId(order.getRide().getId())
                                .routeId(order.getRide().getRoute().getId())
                                .seatId(order.getSeatId())
                                .userId(order.getUser().getId())
                                .status(order.getStatus())
                                .path(order.getRide().getRoute().getStations().stream()
                                        .map(Station::getId)
                                        .toList())
                                .carriages(order.getRide().getRoute().getCarriages().stream()
                                        .map(Carriage::getType)
                                        .toList())
                                .schedule(GetOrdersResponse.Order.Schedule.builder()
                                        .segments(order.getRide().getSegments().stream()
                                                .map(segment -> GetOrdersResponse.Order.Schedule.Segment.builder()
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
                                .build())
                        .toList())
                .build();
    }
}
