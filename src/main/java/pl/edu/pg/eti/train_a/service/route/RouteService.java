package pl.edu.pg.eti.train_a.service.route;

import pl.edu.pg.eti.train_a.entity.Route;

import java.util.List;
import java.util.UUID;

public interface RouteService {
    List<Route> findAll();

    Route findById(UUID id);

    void create(Route route);

    void delete(UUID id);
}
