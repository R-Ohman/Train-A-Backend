package pl.edu.pg.eti.train_a.carriage.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;

import java.util.Optional;

@Repository
public interface CarriageRepository extends JpaRepository<Carriage, Integer> {
    Optional<Carriage> findByType(String type);
}
