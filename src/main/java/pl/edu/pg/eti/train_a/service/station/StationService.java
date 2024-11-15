package pl.edu.pg.eti.train_a.service.station;

import pl.edu.pg.eti.train_a.entity.Station;

import java.math.BigDecimal;
import java.util.List;

public interface StationService {
    List<Station> findAll();

    Station findById(int id);

    Station findNearestStation(BigDecimal latitude, BigDecimal longitude);

    int create(Station station);

    void delete(int id);

    Station findByCityWithDetails(String city);
}
