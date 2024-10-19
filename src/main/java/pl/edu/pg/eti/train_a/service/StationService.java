package pl.edu.pg.eti.train_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.train_a.repository.StationRepository;

@Service
public class StationService {
    private final StationRepository stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }
}
