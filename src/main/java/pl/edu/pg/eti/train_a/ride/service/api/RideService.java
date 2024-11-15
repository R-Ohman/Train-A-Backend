package pl.edu.pg.eti.train_a.ride.service.api;

import pl.edu.pg.eti.train_a.ride.entity.Ride;

import java.util.List;

public interface RideService {
    List<Ride> findAll();

    Ride findById(int id);

    int create(Ride ride);

    void delete(int id);

    Ride findByIdWithDetails(int rideId);
}
