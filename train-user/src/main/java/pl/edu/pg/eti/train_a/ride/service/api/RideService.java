package pl.edu.pg.eti.train_a.ride.service.api;

import pl.edu.pg.eti.train_a.ride.entity.Ride;

import java.util.Optional;

public interface RideService {
    Optional<Ride> findById(int id);
}
