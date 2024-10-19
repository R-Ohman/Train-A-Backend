package pl.edu.pg.eti.train_a.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.eti.train_a.entity.Ride;

public interface RideRepository extends JpaRepository<Ride, Integer> {
}
