package pl.edu.pg.eti.train_a.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.controller.api.CarriageController;
import pl.edu.pg.eti.train_a.dto.GetCarriagesResponse;
import pl.edu.pg.eti.train_a.dto.PostCarriageRequest;
import pl.edu.pg.eti.train_a.function.CarriageToResponseFunction;
import pl.edu.pg.eti.train_a.function.RequestToCarriageFunction;
import pl.edu.pg.eti.train_a.service.carriage.CarriageService;

import java.util.Map;
import java.util.UUID;

@RestController
@Log
public class CarriageDefaultController implements CarriageController {

    private final CarriageService carriageService;
    private final CarriageToResponseFunction carriageToResponse;
    private final RequestToCarriageFunction requestToCarriage;

    @Autowired
    public CarriageDefaultController(
            CarriageService carriageService,
            CarriageToResponseFunction carriageToResponse,
            RequestToCarriageFunction requestToCarriage
            ) {
        this.carriageService = carriageService;
        this.carriageToResponse = carriageToResponse;
        this.requestToCarriage = requestToCarriage;
    }

    @Override
    public GetCarriagesResponse getCarriages() {
        return carriageToResponse.apply(carriageService.findAll());
    }

    @Override
    public Map<String, UUID> postCarriage(PostCarriageRequest request) {
        var newCarriageId = carriageService.create(requestToCarriage.apply(request));
        return Map.of("code", newCarriageId);
    }
}
