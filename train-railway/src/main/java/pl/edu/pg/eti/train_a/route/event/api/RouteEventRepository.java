package pl.edu.pg.eti.train_a.route.event.api;

import pl.edu.pg.eti.train_a.route.entity.Route;

public interface RouteEventRepository {
    void create(Route route);

    void update(Route route);

    void delete(int routeId);
}
