package pl.edu.pg.eti.train_a.ride.service.api;

import pl.edu.pg.eti.train_a.ride.entity.Ride;

import java.util.List;
import java.util.Optional;

public interface RideService {
    List<Ride> findAll();

    Optional<Ride> findById(int id);

    int create(Ride ride);

    int update(Ride ride);

    void delete(int id);

    Optional<Ride> findByIdWithDetails(int rideId);
}
