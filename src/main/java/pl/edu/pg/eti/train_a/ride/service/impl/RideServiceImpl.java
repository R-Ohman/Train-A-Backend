package pl.edu.pg.eti.train_a.ride.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.ride.repository.api.RideRepository;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;

import java.util.List;

@Service
@Transactional
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;

    @Autowired
    public RideServiceImpl(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public List<Ride> findAll() {
        return rideRepository.findAll();
    }

    @Override
    public Ride findById(int id) {
        return rideRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ride not found"));
    }

    public Ride findByIdWithDetails(int rideId) {
        var ride = this.findById(rideId);
        ride.getOrders().size();
        ride.getSegments().size();
        return ride;
    }

    @Override
    public int create(Ride ride) {
        var newRide = this.rideRepository.save(ride);
        return newRide.getId();
    }

    @Override
    public void delete(int id) {
        this.rideRepository.findById(id).ifPresent(rideRepository::delete);
    }
}