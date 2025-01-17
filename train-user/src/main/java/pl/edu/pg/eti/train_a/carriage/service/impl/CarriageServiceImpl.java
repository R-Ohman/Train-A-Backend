package pl.edu.pg.eti.train_a.carriage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;
import pl.edu.pg.eti.train_a.carriage.repository.api.CarriageRepository;
import pl.edu.pg.eti.train_a.carriage.service.api.CarriageService;

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

    @Override
    public Optional<Carriage> findByType(String type) {
        return this.carriageRepository.findByType(type);
    }

    @Override
    public void create(Carriage carriage) {
        this.carriageRepository.save(carriage);
    }

    @Override
    public void update(Carriage carriage) {
        this.create(carriage);
    }

    @Override
    public void delete(UUID id) {
        this.carriageRepository.findById(id).ifPresent(carriageRepository::delete);
    }
}
