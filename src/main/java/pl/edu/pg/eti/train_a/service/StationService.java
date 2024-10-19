package pl.edu.pg.eti.train_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Railway;
import pl.edu.pg.eti.train_a.entity.Station;
import pl.edu.pg.eti.train_a.repository.RailwayRepository;
import pl.edu.pg.eti.train_a.repository.StationRepository;

import java.util.List;

@Service
public class StationService {
    private final StationRepository stationRepository;
    private final RailwayRepository railwayRepository;

    @Autowired
    public StationService(StationRepository stationRepository, RailwayRepository railwayRepository) {
        this.stationRepository = stationRepository;
        this.railwayRepository = railwayRepository;
    }

    @Transactional(readOnly = true)
    public List<Station> findAll() {
        return stationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Railway> findAllRailways() {
        var railways = railwayRepository.findAll();
        railways.forEach(railway -> {
            railway.getStations().size();
        });
        return railways;
    }
}
