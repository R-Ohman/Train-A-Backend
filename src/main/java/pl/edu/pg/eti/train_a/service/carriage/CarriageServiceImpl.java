package pl.edu.pg.eti.train_a.service.carriage;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Carriage;
import pl.edu.pg.eti.train_a.repository.CarriageRepository;
import pl.edu.pg.eti.train_a.repository.RouteRepository;

import java.util.List;

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

    public Carriage findById(int id) {
        return carriageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carriage not found"));
    }

    public Carriage findByType(String type) {
        return carriageRepository.findByType(type).orElseThrow(() -> new EntityNotFoundException("Carriage not found"));
    }

    public Carriage findByTypeWithDetails(String type) {
        routeRepository.findAll();
        var carriage = carriageRepository.findByType(type).orElseThrow(() -> new EntityNotFoundException("Carriage not found"));
        carriage.getRoutes().size();
        carriage.getPrices().size();
        return carriage;
    }

    public void create(Carriage carriage) {
        this.carriageRepository.save(carriage);
    }

    public void delete(int id) {
        this.carriageRepository.findById(id).ifPresent(carriageRepository::delete);
    }
}
