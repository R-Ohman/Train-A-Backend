package pl.edu.pg.eti.train_a.carriage.event.api;

import pl.edu.pg.eti.train_a.carriage.entity.Carriage;

import java.util.UUID;

public interface CarriageEventRepository {
    void create(Carriage carriage);

    void update(Carriage carriage);

    void delete(UUID code);
}
