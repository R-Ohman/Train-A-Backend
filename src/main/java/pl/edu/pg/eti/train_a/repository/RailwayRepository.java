package pl.edu.pg.eti.train_a.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.eti.train_a.entity.Railway;

public interface RailwayRepository extends JpaRepository<Railway, Integer>  {
}
