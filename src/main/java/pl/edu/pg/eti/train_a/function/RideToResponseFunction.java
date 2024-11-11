package pl.edu.pg.eti.train_a.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.dto.GetRideResponse;
import pl.edu.pg.eti.train_a.entity.*;

import java.util.List;
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
                        .segments(ride.getSchedules().stream()
                                .map(schedule -> {
                                    var prices = ride.getPrices().stream()
                                            .filter(price -> price.getRailway().getId().equals(schedule.getRailway().getId()))
                                            .collect(Collectors.toMap(
                                                    price -> price.getCarriage().getType(),
                                                    Price::getPrice
                                            ));

                                    var occupiedSeats = ride.getOrders().stream()
                                            .filter(order -> order.getRide().getId() == ride.getId())
                                            .filter(order -> order.getStatus().equals(OrderStatus.ACTIVE))
                                            .filter(order -> order.getRide().getSchedules().stream()
                                                    .anyMatch(s -> s.getId().equals(schedule.getId())))
                                            .mapToInt(Order::getSeatId)
                                            .boxed()
                                            .toList();

                                    return GetRideResponse.Schedule.Segment.builder()
                                            .time(List.of(schedule.getDepartureTime(), schedule.getArrivalTime()))
                                            .price(prices)
                                            .occupiedSeats(occupiedSeats)
                                            .build();
                                })
                                .toList()
                        )
                        .build()
                )
                .build();
    }
}
