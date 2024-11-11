package pl.edu.pg.eti.train_a.service.route;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Route;
import pl.edu.pg.eti.train_a.entity.Station;
import pl.edu.pg.eti.train_a.repository.RouteRepository;

import java.util.List;

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
    public Route findById(int id) {
        return routeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Route not found"));
    }

    @Override
    public List<Route> findFromToStation(Station from, Station to) {
        return routeRepository.findAll().stream()
                .filter(route -> route.getStations().contains(from) && route.getStations().contains(to))
                .filter(route -> route.getStations().indexOf(from) < route.getStations().indexOf(to))
                .toList();
    }

    public Route findByIdWithDetails(int routeId) {
        Route route = this.findById(routeId);
        route.getRides().size();
        route.getCarriages().size();
        route.getStations().size();
        return route;
    }

    @Override
    public void create(Route route) {
        this.routeRepository.save(route);
    }

    @Override
    public void delete(int routeId) throws Exception {
        var route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));
        this.routeRepository.delete(route);
    }
}
