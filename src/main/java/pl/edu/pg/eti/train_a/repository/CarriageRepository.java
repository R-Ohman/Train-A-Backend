package pl.edu.pg.eti.train_a.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.train_a.entity.Carriage;

import java.util.Optional;

@Repository
public interface CarriageRepository extends JpaRepository<Carriage, Integer> {
    Optional<Carriage> findByType(String type);
}
