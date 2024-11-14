package pl.edu.pg.eti.train_a.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.train_a.entity.Price;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer>  {
    List<Price> findPricesByRideId(int rideId);
}
