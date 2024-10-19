package pl.edu.pg.eti.train_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pg.eti.train_a.entity.Carriage;
import pl.edu.pg.eti.train_a.repository.CarriageRepository;

import java.util.List;

@Service
public class CarriageService {
    private final CarriageRepository carriageRepository;

    @Autowired
    public CarriageService(CarriageRepository carriageRepository) {
        this.carriageRepository = carriageRepository;
    }

    @Transactional(readOnly = true)
    public List<Carriage> findAll() {
        return carriageRepository.findAll();
    }
}
