package pl.edu.pg.eti.train_a.carriage.service.api;

import pl.edu.pg.eti.train_a.carriage.entity.Carriage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarriageService {
    List<Carriage> findAll();

    Optional<Carriage> findById(UUID id);

    Optional<Carriage> findByType(String type);

    Optional<Carriage> findByCode(String code);

    UUID create(Carriage carriage);

    UUID update(Carriage carriage);

    void delete(UUID id);
}
