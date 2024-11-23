package pl.edu.pg.eti.train_a.station.service.api;

import pl.edu.pg.eti.train_a.station.entity.Station;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StationService {
    Optional<Station> findById(int id);
}
