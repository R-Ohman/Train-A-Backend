package pl.edu.pg.eti.train_a.service.route;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.train_a.entity.Route;
import pl.edu.pg.eti.train_a.repository.RouteRepository;

import java.util.List;

@Service
@Transactional
public class RouteServiceImpl {
    private final RouteRepository routeRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Route findById(int id) {
        return routeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Route not found"));
    }

    public Route findByIdWithDetails(int routeId) {
        Route route = this.findById(routeId);
        route.getRides().size();
        route.getCarriages().size();
        route.getStations().size();
        return route;
    }

    public void create(Route route) {
        this.routeRepository.save(route);
    }

    public void delete(int routeId) throws Exception {
        var route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));
        this.routeRepository.delete(route);
    }
}
