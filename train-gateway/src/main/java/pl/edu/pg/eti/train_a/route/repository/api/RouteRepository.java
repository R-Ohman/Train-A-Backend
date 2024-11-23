package pl.edu.pg.eti.train_a.route.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.train_a.route.entity.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer>  {
}
