package pl.edu.pg.eti.train_a.ride.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.train_a.ride.entity.Ride;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {
}
