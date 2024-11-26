package pl.edu.pg.eti.train_a.carriage.service.api;

import pl.edu.pg.eti.train_a.carriage.entity.Carriage;

import java.util.UUID;

public interface CarriageService {
    void create(Carriage carriage);

    void update(Carriage carriage);

    void delete(UUID carriageId);
}
