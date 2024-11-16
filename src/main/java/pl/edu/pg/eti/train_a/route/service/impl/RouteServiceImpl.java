package pl.edu.pg.eti.train_a.route.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.route.repository.api.RouteRepository;
import pl.edu.pg.eti.train_a.route.service.api.RouteService;
import pl.edu.pg.eti.train_a.station.entity.Station;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    @Override
    public Optional<Route> findById(int id) {
        return routeRepository.findById(id);
    }

    @Override
    public List<Route> findFromToStation(Station from, Station to) {
        return routeRepository.findAll().stream()
                .filter(route -> route.getStations().contains(from) && route.getStations().contains(to))
                .filter(route -> route.getStations().indexOf(from) < route.getStations().indexOf(to))
                .toList();
    }

    public Route findByIdWithDetails(int routeId) {
        Route route = this.findById(routeId).orElseThrow();
        route.getRides().size();
        route.getCarriages().size();
        route.getStations().size();
        return route;
    }

    @Override
    public int create(Route route) {
        var newRoute = this.routeRepository.save(route);
        return newRoute.getId();
    }

    @Override
    public int update(Route route) {
        return this.create(route);
    }

    @Override
    public void delete(int routeId) {
        var route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));
        this.routeRepository.delete(route);
    }
}
