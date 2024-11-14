package pl.edu.pg.eti.train_a.service.railway;

import pl.edu.pg.eti.train_a.entity.Railway;

import java.util.List;

public interface RailwayService {
    List<Railway> findAll();

    Railway findById(int id);

    void create(Railway railway);

    void delete(int id);
}
