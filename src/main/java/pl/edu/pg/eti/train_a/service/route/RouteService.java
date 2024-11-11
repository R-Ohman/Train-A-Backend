package pl.edu.pg.eti.train_a.service.route;

import pl.edu.pg.eti.train_a.entity.Route;
import pl.edu.pg.eti.train_a.entity.Station;

import java.util.List;

public interface RouteService {
    List<Route> findAll();

    Route findById(int id);

    List<Route> findFromToStation(Station from, Station to);

    void create(Route route);

    void delete(int id) throws Exception;
}
