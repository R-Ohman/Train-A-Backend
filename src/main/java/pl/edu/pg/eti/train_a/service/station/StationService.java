package pl.edu.pg.eti.train_a.service.station;

import pl.edu.pg.eti.train_a.entity.Station;

import java.util.List;
import java.util.UUID;

public interface StationService {
    List<Station> findAll();

    Station findById(UUID id);

    Station findNearestStation(double latitude, double longitude);

    void create(Station station);

    void delete(UUID id);
}
