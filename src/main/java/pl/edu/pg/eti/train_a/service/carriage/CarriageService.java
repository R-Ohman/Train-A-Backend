package pl.edu.pg.eti.train_a.service.carriage;

import pl.edu.pg.eti.train_a.entity.Carriage;

import java.util.List;
import java.util.UUID;

public interface CarriageService {
    List<Carriage> findAll();

    Carriage findById(int id);

    Carriage findByType(String type);

    Carriage findByTypeWithDetails(String type);

    UUID create(Carriage carriage);

    void delete(int id);
}
