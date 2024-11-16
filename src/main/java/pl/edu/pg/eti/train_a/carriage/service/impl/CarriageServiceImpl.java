package pl.edu.pg.eti.train_a.carriage.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;
import pl.edu.pg.eti.train_a.carriage.repository.api.CarriageRepository;
import pl.edu.pg.eti.train_a.carriage.service.api.CarriageService;
import pl.edu.pg.eti.train_a.route.repository.api.RouteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CarriageServiceImpl implements CarriageService {
    private final CarriageRepository carriageRepository;
    private final RouteRepository routeRepository;

    @Autowired
    public CarriageServiceImpl(CarriageRepository carriageRepository, RouteRepository routeRepository) {
        this.carriageRepository = carriageRepository;
        this.routeRepository = routeRepository;
    }

    public List<Carriage> findAll() {
        return carriageRepository.findAll();
    }

    public Optional<Carriage> findById(UUID id) {
        return carriageRepository.findById(id);
    }

    public Carriage findByType(String type) {
        return carriageRepository.findByType(type).orElseThrow(() -> new EntityNotFoundException("Carriage not found"));
    }

    public Carriage findByTypeWithDetails(String type) {
        routeRepository.findAll();
        var carriage = carriageRepository.findByType(type).orElseThrow(() -> new EntityNotFoundException("Carriage not found"));
        carriage.getRoutes().size();
        return carriage;
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
