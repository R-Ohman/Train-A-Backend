package pl.edu.pg.eti.train_a.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Ride;
import pl.edu.pg.eti.train_a.repository.RideRepository;

import java.util.List;

@Service
public class RideService {
    private final RideRepository rideRepository;

    @Autowired
    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Transactional(readOnly = true)
    public List<Ride> findAll() {
        return rideRepository.findAll();
    }

    @Transactional
    public Ride getRideWithLazyProperties(int rideId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new EntityNotFoundException());
        ride.getPrices().size();
        ride.getSchedules().size();
        ride.getOrders().size();
        return ride;
    }
}
