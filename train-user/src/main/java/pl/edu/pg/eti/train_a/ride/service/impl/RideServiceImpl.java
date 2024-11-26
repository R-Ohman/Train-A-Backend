package pl.edu.pg.eti.train_a.ride.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.ride.repository.api.RideRepository;
import pl.edu.pg.eti.train_a.ride.repository.api.SegmentRepository;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;

import java.util.Optional;

@Service
@Transactional
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final SegmentRepository segmentRepository;

    @Autowired
    public RideServiceImpl(RideRepository rideRepository, SegmentRepository segmentRepository) {
        this.rideRepository = rideRepository;
        this.segmentRepository = segmentRepository;
    }

    @Override
    public Optional<Ride> findById(int id) {
        return rideRepository.findById(id);
    }

    @Override
    public int create(Ride ride) {
        var newRide = this.rideRepository.save(ride);
        return newRide.getId();
    }

    @Override
    public int update(Ride ride) {
        this.segmentRepository.saveAll(ride.getSegments());
        return ride.getId();
    }

    @Override
    public void delete(int id) {
        this.rideRepository.findById(id).ifPresent(rideRepository::delete);
    }
}
