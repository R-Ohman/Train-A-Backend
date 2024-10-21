package pl.edu.pg.eti.train_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Ride;
import pl.edu.pg.eti.train_a.repository.RideRepository;

import java.util.List;

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final RouteService routeService;

    @Autowired
    public RideService(RideRepository rideRepository, RouteService routeService) {
        this.rideRepository = rideRepository;
        this.routeService = routeService;
    }

    @Transactional(readOnly = true)
    public List<Ride> findAll() {
        var rides = rideRepository.findAll();
        rides.forEach(ride -> {
            ride.getSchedules().size();
            ride.getOrders().size();
            ride.getPrices().size();
            routeService.findById(ride.getRoute().getId());
        });
        return rides;
    }
}
