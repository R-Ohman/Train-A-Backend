package pl.edu.pg.eti.train_a.service.ride;

import pl.edu.pg.eti.train_a.entity.Ride;

import java.util.List;
import java.util.UUID;

public interface RideService {
    List<Ride> findAll();

    Ride findById(UUID id);

    void create(Ride ride);

    void delete(UUID id);
}
