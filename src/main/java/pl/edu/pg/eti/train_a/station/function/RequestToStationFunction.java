package pl.edu.pg.eti.train_a.station.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.station.dto.PostStationRequest;
import pl.edu.pg.eti.train_a.station.entity.Railway;
import pl.edu.pg.eti.train_a.station.entity.Station;
import pl.edu.pg.eti.train_a.station.service.api.StationService;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestToStationFunction implements Function<PostStationRequest, Station> {

    private final StationService stationService;

    @Autowired
    public RequestToStationFunction(StationService stationService) {
        this.stationService = stationService;

    }

    @Override
    public Station apply(PostStationRequest request) {
        var station = stationService.findNearestStation(request.getLatitude(), request.getLongitude());
        return Station.builder()
                .city(request.getCity())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .railways(request.getRelations().stream()
                        .map((connectedStationId) -> Railway.autoBuilder()
                                .stations(List.of(station, stationService.findById(connectedStationId)))
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .build();
    }
}
