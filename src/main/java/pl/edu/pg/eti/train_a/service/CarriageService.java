package pl.edu.pg.eti.train_a.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pg.eti.train_a.repository.CarriageRepository;

@Service
public class CarriageService {
    private final CarriageRepository carriageRepository;

    @Autowired
    public CarriageService(CarriageRepository carriageRepository) {
        this.carriageRepository = carriageRepository;
    }
}
