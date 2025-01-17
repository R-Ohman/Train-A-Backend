package pl.edu.pg.eti.train_a.station.event.api;

import pl.edu.pg.eti.train_a.station.entity.Station;

public interface StationEventRepository {
    void create(Station station);

    void delete(int stationId);
}
