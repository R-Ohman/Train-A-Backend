package pl.edu.pg.eti.train_a.station.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.station.entity.Station;
import pl.edu.pg.eti.train_a.station.repository.api.StationRepository;
import pl.edu.pg.eti.train_a.station.service.api.StationService;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class StationServiceImpl implements StationService {
    private final StationRepository stationRepository;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public List<Station> findAll() {
        return stationRepository.findAll();
    }

    @Override
    public Station findById(int id) {
        return stationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Station not found"));
    }

    @Override
    public Station findNearestStation(BigDecimal latitude, BigDecimal longitude) {
        return stationRepository.findAll().stream()
                .min((s1, s2) -> {
                    var d1 = Math.pow(s1.getLatitude().subtract(latitude).doubleValue(), 2) + Math.pow(s1.getLongitude().subtract(longitude).doubleValue(), 2);
                    var d2 = Math.pow(s2.getLatitude().subtract(latitude).doubleValue(), 2) + Math.pow(s2.getLongitude().subtract(longitude).doubleValue(), 2);
                    return Double.compare(d1, d2);
                })
                .orElseThrow(() -> new EntityNotFoundException("Station not found"));
    }

    public Station findByCityWithDetails(String city) {
        var station = stationRepository.findByCity(city).orElseThrow(() -> new EntityNotFoundException("Station not found"));
        station.getRoutes().size();
        station.getRailways().size();
        station.getOrdersFrom().size();
        station.getOrdersTo().size();
        return station;
    }

    @Override
    public int create(Station station) {
        var newStation = this.stationRepository.save(station);
        return newStation.getId();
    }

    @Override
    public void delete(int id) {
        this.stationRepository.findById(id).ifPresent(stationRepository::delete);
    }
}
