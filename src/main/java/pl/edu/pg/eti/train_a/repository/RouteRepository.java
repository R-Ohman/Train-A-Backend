package pl.edu.pg.eti.train_a.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.eti.train_a.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Integer>  {
}
