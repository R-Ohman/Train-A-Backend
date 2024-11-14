package pl.edu.pg.eti.train_a.service.station;

import pl.edu.pg.eti.train_a.entity.Station;

import java.util.List;

public interface StationService {
    List<Station> findAll();

    Station findById(int id);

    Station findNearestStation(double latitude, double longitude);

    void create(Station station);

    void delete(int id);
}
