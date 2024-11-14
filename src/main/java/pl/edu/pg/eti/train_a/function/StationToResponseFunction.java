package pl.edu.pg.eti.train_a.function;

import org.springframework.stereotype.Component;
import pl.edu.pg.eti.train_a.dto.GetStationsResponse;
import pl.edu.pg.eti.train_a.entity.Station;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StationToResponseFunction implements Function<List<Station>, GetStationsResponse> {

    @Override
    public GetStationsResponse apply(List<Station> stations) {
        return GetStationsResponse.builder()
                .stations(stations.stream()
                        .map(station -> GetStationsResponse.Station.builder()
                                .id(station.getId())
                                .city(station.getCity())
                                .latitude(station.getLatitude())
                                .longitude(station.getLongitude())
                                .connectedTo(station.getRailways().stream()
                                        .map(railway -> GetStationsResponse.Station.Connection.builder()
                                                .stationId(railway.getStations().stream()
                                                        .filter(s -> !(s.getId() == station.getId()))
                                                        .findFirst()
                                                        .orElseThrow(() -> new IllegalStateException("Station not found"))
                                                        .getId()
                                                )
                                                .distance(railway.getDistance())
                                                .build()
                                        )
                                        .collect(Collectors.toList())
                                )
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .build();
    }
}
