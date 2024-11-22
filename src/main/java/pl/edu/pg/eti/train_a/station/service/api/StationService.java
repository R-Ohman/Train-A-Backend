package pl.edu.pg.eti.train_a.station.service.api;

import pl.edu.pg.eti.train_a.station.entity.Station;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StationService {
    List<Station> findAll();

    Optional<Station> findById(int id);

    Station findNearestStation(BigDecimal latitude, BigDecimal longitude);

    int create(Station station);

    void delete(int id);

    Optional<Station> findByCityWithDetails(String city);
}
