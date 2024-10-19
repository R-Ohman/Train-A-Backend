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
        return routeRepository.findAll();
    }
}
