package pl.edu.pg.eti.train_a.route.service.api;

import pl.edu.pg.eti.train_a.route.entity.Route;

import java.util.Optional;

public interface RouteService {
    Optional<Route> findById(int id);

    int create(Route route);

    void update(Route route);

    void delete(int id);
}
