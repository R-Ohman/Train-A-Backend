package pl.edu.pg.eti.train_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.train_a.repository.RideRepository;

@Service
public class RideService {
    private final RideRepository rideRepository;

    @Autowired
    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }
}
