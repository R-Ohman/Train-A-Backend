package pl.edu.pg.eti.train_a.ride.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.ride.repository.api.RideRepository;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;

import java.util.Optional;

@Service
@Transactional
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;

    @Autowired
    public RideServiceImpl(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public Optional<Ride> findById(int id) {
        return rideRepository.findById(id);
    }
}
