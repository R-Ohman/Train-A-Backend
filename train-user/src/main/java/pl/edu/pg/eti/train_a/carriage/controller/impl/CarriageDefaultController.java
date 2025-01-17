package pl.edu.pg.eti.train_a.carriage.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.carriage.controller.api.CarriageController;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;
import pl.edu.pg.eti.train_a.carriage.service.api.CarriageService;

import java.util.UUID;

@RestController
@Log
public class CarriageDefaultController implements CarriageController {
    private final CarriageService carriageService;

    @Autowired
    public CarriageDefaultController(CarriageService carriageService) {
        this.carriageService = carriageService;
    }

    @Override
    public void postCarriage(Carriage carriage) {
        carriageService.create(carriage);
    }

    @Override
    public void putCarriage(Carriage carriage) {
        carriageService.update(carriage);
    }

    @Override
    public void deleteCarriage(UUID carriageId) {
        carriageService.delete(carriageId);
    }
}
