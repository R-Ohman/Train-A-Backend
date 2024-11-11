package pl.edu.pg.eti.train_a.service.ride;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Ride;
import pl.edu.pg.eti.train_a.repository.RideRepository;

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
        Ride ride = this.findById(rideId);
        ride.getPrices().size();
        ride.getSchedules().size();
        ride.getOrders().size();
        return ride;
    }

    @Override
    public void create(Ride ride) {
        this.rideRepository.save(ride);
    }

    @Override
    public void delete(int id) {
        this.rideRepository.findById(id).ifPresent(rideRepository::delete);
    }
}
