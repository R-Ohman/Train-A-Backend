package pl.edu.pg.eti.train_a.ride.event.api;

import pl.edu.pg.eti.train_a.ride.dto.PostRideRequest;
import pl.edu.pg.eti.train_a.ride.dto.PutRideRequest;

public interface RideEventRepository {
    void create(int routeId, PostRideRequest request);

    void update(int routeId, int rideId, PutRideRequest request);

    void delete(int rideId);
}
