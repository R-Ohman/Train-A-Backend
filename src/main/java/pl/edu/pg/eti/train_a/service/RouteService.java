package pl.edu.pg.eti.train_a.service;

import jakarta.persistence.EntityNotFoundException;
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
        return routeRepository.findAll();
    }

    @Transactional
    public Route getRouteWithLazyProperties(int routeId) {
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new EntityNotFoundException());
        route.getRides().size();
        route.getCarriages().size();
        route.getStations().size();
        return route;
    }
}
