package pl.edu.pg.eti.train_a.station.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.train_a.station.entity.Railway;

@Repository
public interface RailwayRepository extends JpaRepository<Railway, Integer>  {
}