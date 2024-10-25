package pl.edu.pg.eti.train_a.service.railway;

import pl.edu.pg.eti.train_a.entity.Railway;

import java.util.List;
import java.util.UUID;

public interface RailwayService {
    List<Railway> findAll();

    Railway findById(UUID id);

    void create(Railway railway);

    void delete(UUID id);
}
