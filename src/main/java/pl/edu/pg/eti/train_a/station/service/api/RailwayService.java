package pl.edu.pg.eti.train_a.station.service.api;

import pl.edu.pg.eti.train_a.station.entity.Railway;

import java.util.List;

public interface RailwayService {
    List<Railway> findAll();

    Railway findById(int id);

    void create(Railway railway);

    void delete(int id);
}
