package pl.edu.pg.eti.train_a.ride.event.api;

import pl.edu.pg.eti.train_a.ride.entity.Ride;
import pl.edu.pg.eti.train_a.user.dto.SignInRequest;
import pl.edu.pg.eti.train_a.user.entity.User;

public interface RideEventRepository {
    void create(Ride ride);

    void update(Ride ride);

    void delete(int rideId);
}
