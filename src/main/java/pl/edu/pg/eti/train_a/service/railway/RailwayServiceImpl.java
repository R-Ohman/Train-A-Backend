package pl.edu.pg.eti.train_a.service.railway;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Railway;
import pl.edu.pg.eti.train_a.repository.RailwayRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RailwayServiceImpl {
    private final RailwayRepository railwayRepository;

    @Autowired
    public RailwayServiceImpl(RailwayRepository railwayRepository) {
        this.railwayRepository = railwayRepository;
    }

    public List<Railway> findAll() {
        return railwayRepository.findAll();
    }

    public Railway findById(UUID id) {
        return railwayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Railway not found"));
    }

    public void create(Railway railway) {
        railwayRepository.save(railway);
    }

    public void delete(UUID id) {
        railwayRepository.findById(id).ifPresent(railwayRepository::delete);
    }
}
