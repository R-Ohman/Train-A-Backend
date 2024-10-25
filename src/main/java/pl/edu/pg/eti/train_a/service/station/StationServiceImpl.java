package pl.edu.pg.eti.train_a.service.station;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Station;
import pl.edu.pg.eti.train_a.repository.StationRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class StationServiceImpl {
    private final StationRepository stationRepository;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public List<Station> findAll() {
        return stationRepository.findAll();
    }

    public Station findById(UUID id) {
        return stationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Station not found"));
    }

    public void create(Station station) {
        this.stationRepository.save(station);
    }

    public void delete(UUID id) {
        this.stationRepository.findById(id).ifPresent(stationRepository::delete);
    }
}
