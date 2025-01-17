package pl.edu.pg.eti.train_a.carriage.controller.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.eti.train_a.exception.CustomResponseStatusException;
import pl.edu.pg.eti.train_a.carriage.controller.api.CarriageController;
import pl.edu.pg.eti.train_a.carriage.dto.GetCarriagesResponse;
import pl.edu.pg.eti.train_a.carriage.dto.PostCarriageRequest;
import pl.edu.pg.eti.train_a.carriage.dto.PutCarriageRequest;
import pl.edu.pg.eti.train_a.carriage.function.CarriageToResponseFunction;
import pl.edu.pg.eti.train_a.carriage.function.RequestToCarriageFunction;
import pl.edu.pg.eti.train_a.carriage.service.api.CarriageService;

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

    @Override
    public Map<String, UUID> putCarriage(UUID code, PutCarriageRequest request) {
        var updatedCarriageId = carriageService.update(requestToCarriage.apply(code, request));
        return Map.of("code", updatedCarriageId);
    }

    @Override
    public void deleteCarriage(UUID code) {
        carriageService.findById(code)
                .ifPresentOrElse(
                        c -> carriageService.delete(code),
                        () -> {
                            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "carriageNotFound", "Carriage not found");
                        }
                );
    }
}
