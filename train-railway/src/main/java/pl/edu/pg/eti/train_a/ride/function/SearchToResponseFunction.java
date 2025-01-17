package pl.edu.pg.eti.train_a.ride.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.util.QuadFunction;
import pl.edu.pg.eti.train_a.ride.dto.GetSearchResponse;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;
import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.station.entity.Station;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SearchToResponseFunction implements QuadFunction<Station, Station, List<Route>, Optional<LocalDateTime>, GetSearchResponse> {
    private final RideToResponseFunction rideToResponseFunction;

    public SearchToResponseFunction(RideToResponseFunction rideToResponseFunction) {
        this.rideToResponseFunction = rideToResponseFunction;
    }

    @Override
    public GetSearchResponse apply(Station from, Station to, List<Route> routes, Optional<LocalDateTime> time) {
        return GetSearchResponse.builder()
                .from(GetSearchResponse.Station.builder()
                        .stationId(from.getId())
                        .city(from.getCity())
                        .geolocation(GetSearchResponse.Station.Geolocation.builder()
                                .latitude(from.getLatitude())
                                .longitude(from.getLongitude())
                                .build())
                        .build())
                .to(GetSearchResponse.Station.builder()
                        .stationId(to.getId())
                        .city(to.getCity())
                        .geolocation(GetSearchResponse.Station.Geolocation.builder()
                                .latitude(to.getLatitude())
                                .longitude(to.getLongitude())
                                .build())
                        .build())
                .routes(routes.stream()
                        .map(route -> GetSearchResponse.Route.builder()
                                .id(route.getId())
                                .path(route.getStations().stream()
                                        .map(Station::getId)
                                        .toList())
                                .carriages(
                                        route.getCarriages().stream()
                                                .map(Carriage::getType)
                                                .collect(Collectors.toList()
                                                )
                                )
                                .schedule(route.getRides().stream().map(ride -> {
                                            var segments = rideToResponseFunction.apply(ride).getSchedule().getSegments();
                                            if (time.isPresent() && !segments.isEmpty()) {
                                                var lastStationArrivalTime = segments.get(segments.size() - 1).getTime().get(1);
                                                if (lastStationArrivalTime.isBefore(time.get())) {
                                                    return null;
                                                }
                                            }

                                            return GetSearchResponse.Route.Schedule.builder()
                                                    .rideId(ride.getId())
                                                    .segments(segments)
                                                    .build();
                                        })
                                        .filter(Objects::nonNull)
                                        .toList())
                                .build())
                        .filter(route -> !route.getSchedule().isEmpty())
                        .toList())
                .build();
    }
}
