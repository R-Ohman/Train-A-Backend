package pl.edu.pg.eti.train_a.station.service.api;

import pl.edu.pg.eti.train_a.station.entity.Railway;

import java.util.List;
import java.util.Optional;

public interface RailwayService {
    List<Railway> findAll();

    Optional<Railway> findById(int id);

    void create(Railway railway);

    void delete(int id);
}
