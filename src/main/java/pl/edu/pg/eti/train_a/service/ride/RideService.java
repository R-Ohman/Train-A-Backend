package pl.edu.pg.eti.train_a.service.ride;

import pl.edu.pg.eti.train_a.entity.Ride;

import java.util.List;

public interface RideService {
    List<Ride> findAll();

    Ride findById(int id);

    void create(Ride ride);

    void delete(int id);
}
