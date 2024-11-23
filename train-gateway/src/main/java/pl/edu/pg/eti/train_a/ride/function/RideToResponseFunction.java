package pl.edu.pg.eti.train_a.ride.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;
import pl.edu.pg.eti.train_a.ride.dto.GetRideResponse;
import pl.edu.pg.eti.train_a.ride.dto.RideSegmentResponse;
import pl.edu.pg.eti.train_a.order.entity.Order;
import pl.edu.pg.eti.train_a.order.entity.OrderStatus;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.station.entity.Station;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RideToResponseFunction implements Function<Ride, GetRideResponse> {
    @Override
    public GetRideResponse apply(Ride ride) {
        return GetRideResponse.builder()
                .rideId(ride.getId())
                .routeId(ride.getRoute().getId())
                .path(
                        ride.getRoute().getStations().stream()
                                .map(Station::getId)
                                .collect(Collectors.toList())
                )
                .carriages(
                        ride.getRoute().getCarriages().stream()
                                .map(Carriage::getType)
                                .collect(Collectors.toList()
                                )
                )
                .schedule(GetRideResponse.Schedule.builder()
                        .segments(ride.getSegments().stream()
                                .map(segment -> {
                                    var occupiedSeats = ride.getOrders().stream()
                                            .filter(order -> order.getRide().getId() == ride.getId()
                                                    && order.getStatus().equals(OrderStatus.ACTIVE))
                                            .filter(order -> order.getRide().getSegments().stream()
                                                    .anyMatch(s -> s.getId() == segment.getId()))
                                            .mapToInt(Order::getSeatId)
                                            .boxed()
                                            .toList();

                                    return RideSegmentResponse.builder()
                                            .time(List.of(segment.getDeparture(), segment.getArrival()))
                                            .price(
                                                    segment.getPrices().entrySet().stream()
                                                            .collect(Collectors.toMap(
                                                                    entry -> entry.getKey().getType(),
                                                                    Map.Entry::getValue))
                                            )
                                            .occupiedSeats(occupiedSeats)
                                            .build();
                                })
                                .toList())
                        .build())
                .build();
    }
}
