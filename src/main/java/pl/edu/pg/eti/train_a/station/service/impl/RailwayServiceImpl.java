package pl.edu.pg.eti.train_a.station.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.station.entity.Railway;
import pl.edu.pg.eti.train_a.station.repository.api.RailwayRepository;
import pl.edu.pg.eti.train_a.station.service.api.RailwayService;

import java.util.List;

@Service
@Transactional
public class RailwayServiceImpl implements RailwayService {
    private final RailwayRepository railwayRepository;

    @Autowired
    public RailwayServiceImpl(RailwayRepository railwayRepository) {
        this.railwayRepository = railwayRepository;
    }

    @Override
    public List<Railway> findAll() {
        return railwayRepository.findAll();
    }

    @Override
    public Railway findById(int id) {
        return railwayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Railway not found"));
    }

    @Override
    public void create(Railway railway) {
        railwayRepository.save(railway);
    }

    @Override
    public void delete(int id) {
        railwayRepository.findById(id).ifPresent(railwayRepository::delete);
    }
}
