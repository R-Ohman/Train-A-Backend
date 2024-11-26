package pl.edu.pg.eti.train_a.route.service.api;

import pl.edu.pg.eti.train_a.route.entity.Route;

public interface RouteService {
    int create(Route route);

    void update(Route route);

    void delete(int id);
}
