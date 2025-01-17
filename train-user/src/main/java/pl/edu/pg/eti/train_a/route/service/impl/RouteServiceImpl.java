package pl.edu.pg.eti.train_a.route.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.route.entity.Route;
import pl.edu.pg.eti.train_a.route.repository.api.RouteRepository;
import pl.edu.pg.eti.train_a.route.service.api.RouteService;

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
    public Optional<Route> findById(int id) {
        return this.routeRepository.findById(id);
    }

    @Override
    public int create(Route route) {
        var newRoute = this.routeRepository.save(route);
        return newRoute.getId();
    }

    @Override
    public void update(Route route) {
        this.create(route);
    }

    @Override
    public void delete(int id) {
        this.routeRepository.findById(id).ifPresent(routeRepository::delete);
    }
}
