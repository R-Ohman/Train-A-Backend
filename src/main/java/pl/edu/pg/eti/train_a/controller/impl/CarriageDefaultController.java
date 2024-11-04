package pl.edu.pg.eti.train_a.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.controller.api.CarriageController;
import pl.edu.pg.eti.train_a.dto.GetCarriagesResponse;
import pl.edu.pg.eti.train_a.function.CarriageToResponseFunction;
import pl.edu.pg.eti.train_a.service.carriage.CarriageServiceImpl;

@RestController
@Log
public class CarriageDefaultController implements CarriageController {

    private final CarriageServiceImpl carriageService;
    private final CarriageToResponseFunction carriageToResponse;

    @Autowired
    public CarriageDefaultController(CarriageServiceImpl carriageService, CarriageToResponseFunction carriageToResponse) {
        this.carriageService = carriageService;
        this.carriageToResponse = carriageToResponse;
    }

    @Override
    public GetCarriagesResponse getCarriages() {
        return carriageToResponse.apply(carriageService.findAll());
    }
}
