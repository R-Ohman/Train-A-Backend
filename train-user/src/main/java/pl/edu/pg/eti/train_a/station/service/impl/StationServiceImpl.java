package pl.edu.pg.eti.train_a.station.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.station.entity.Station;
import pl.edu.pg.eti.train_a.station.repository.api.StationRepository;
import pl.edu.pg.eti.train_a.station.service.api.StationService;

import java.util.Optional;

@Service
@Transactional
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public Optional<Station> findById(int id) {
        return stationRepository.findById(id);
    }
}
