package pl.edu.pg.eti.train_a.route.service.api;

import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.station.entity.Station;

import java.util.List;
import java.util.Optional;

public interface RouteService {
    List<Route> findAll();

    Optional<Route> findById(int id);

    List<Route> findFromToStation(Station from, Station to);

    int create(Route route);

    int update(Route route);

    void delete(int id);
}
