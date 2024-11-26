package pl.edu.pg.eti.train_a.ride.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.ride.event.api.RideEventRepository;
import pl.edu.pg.eti.train_a.ride.repository.api.RideRepository;
import pl.edu.pg.eti.train_a.ride.repository.api.SegmentRepository;
import pl.edu.pg.eti.train_a.ride.service.api.RideService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final SegmentRepository segmentRepository;
    private final RideEventRepository rideEventRepository;

    @Autowired
    public RideServiceImpl(
            RideRepository rideRepository,
            SegmentRepository segmentRepository,
            RideEventRepository rideEventRepository
    ) {
        this.rideRepository = rideRepository;
        this.segmentRepository = segmentRepository;
        this.rideEventRepository = rideEventRepository;
    }

    @Override
    public List<Ride> findAll() {
        return rideRepository.findAll();
    }

    @Override
    public Optional<Ride> findById(int id) {
        return rideRepository.findById(id);
    }

    @Override
    public int create(Ride ride) {
        var newRide = this.rideRepository.save(ride);
        this.rideEventRepository.create(newRide);
        return newRide.getId();
    }

    @Override
    public int update(Ride ride) {
        this.segmentRepository.saveAll(ride.getSegments());
        this.rideEventRepository.update(ride);
        return ride.getId();
    }

    @Override
    public void delete(int id) {
        this.rideRepository.findById(id).ifPresent(ride -> {
            this.rideRepository.delete(ride);
            this.rideEventRepository.delete(ride.getId());
        });
    }
}
