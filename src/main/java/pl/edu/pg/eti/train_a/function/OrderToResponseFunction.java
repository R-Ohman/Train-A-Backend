package pl.edu.pg.eti.train_a.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.dto.GetOrdersResponse;
import pl.edu.pg.eti.train_a.entity.Carriage;
import pl.edu.pg.eti.train_a.entity.Order;
import pl.edu.pg.eti.train_a.entity.Price;
import pl.edu.pg.eti.train_a.entity.Station;

import java.util.List;
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
                                        .segments(order.getRide().getSchedules().stream()
                                                .map(schedule -> {
                                                    var prices = order.getRide().getPrices().stream()
                                                            .filter(price -> price.getRailway().getId().equals(schedule.getRailway().getId()))
                                                            .collect(Collectors.toMap(
                                                                    price -> price.getCarriage().getType(),
                                                                    Price::getPrice
                                                            ));
                                                    return GetOrdersResponse.Order.Schedule.Segment.builder()
                                                            .time(List.of(schedule.getDepartureTime(), schedule.getArrivalTime()))
                                                            .price(prices)
                                                            .build();
                                                })
                                                .toList())
                                        .build())
                                .build())
                        .toList())
                .build();
    }
}
