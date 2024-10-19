package pl.edu.pg.eti.train_a.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.train_a.entity.Route;
import pl.edu.pg.eti.train_a.repository.RouteRepository;

import java.util.List;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Transactional(readOnly = true)
    public List<Route> findAll() {
        var routes = routeRepository.findAll();
        routes.forEach(this::loadLazyCollections);
        return routes;
    }

    @Transactional(readOnly = true)
    public Route findById(int id) {
        var route = routeRepository.findById(id).get();
        loadLazyCollections(route);
        return route;
    }

    private void loadLazyCollections(Route route) {
        route.getStations().size();
        route.getRides().size();
        route.getCarriages().size();
    }
}
