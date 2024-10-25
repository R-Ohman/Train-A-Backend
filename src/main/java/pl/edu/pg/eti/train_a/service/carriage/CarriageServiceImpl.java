package pl.edu.pg.eti.train_a.service.carriage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Carriage;
import pl.edu.pg.eti.train_a.repository.CarriageRepository;

import java.util.List;
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

    public Carriage findById(UUID id) {
        return carriageRepository.findById(id).orElse(null);
    }

    public void create(Carriage carriage) {
        this.carriageRepository.save(carriage);
    }

    public void delete(UUID id) {
        this.carriageRepository.findById(id).ifPresent(carriageRepository::delete);
    }
}
