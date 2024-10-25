package pl.edu.pg.eti.train_a.service.carriage;

import pl.edu.pg.eti.train_a.entity.Carriage;

import java.util.List;
import java.util.UUID;

public interface CarriageService {
    List<Carriage> findAll();

    Carriage findById(UUID id);

    void create(Carriage carriage);

    void delete(UUID id);
}
