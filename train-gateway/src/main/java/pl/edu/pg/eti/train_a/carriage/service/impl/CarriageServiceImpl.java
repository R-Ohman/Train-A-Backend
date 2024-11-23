package pl.edu.pg.eti.train_a.carriage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;
import pl.edu.pg.eti.train_a.carriage.repository.api.CarriageRepository;
import pl.edu.pg.eti.train_a.carriage.service.api.CarriageService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CarriageServiceImpl implements CarriageService {
    private final CarriageRepository carriageRepository;

    @Autowired
    public CarriageServiceImpl(CarriageRepository carriageRepository) {
        this.carriageRepository = carriageRepository;
    }

    public List<Carriage> findAll() {
        return carriageRepository.findAll();
    }

    public Optional<Carriage> findById(UUID id) {
        return carriageRepository.findById(id);
    }

    public Optional<Carriage> findByType(String type) {
        return carriageRepository.findByType(type);
    }

    public UUID create(Carriage carriage) {
        var newCarriage = this.carriageRepository.save(carriage);
        return newCarriage.getCode();
    }

    public UUID update(Carriage carriage) {
        return this.create(carriage);
    }

    public void delete(UUID id) {
        this.carriageRepository.findById(id).ifPresent(carriageRepository::delete);
    }
}
