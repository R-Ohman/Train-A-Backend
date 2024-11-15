package pl.edu.pg.eti.train_a.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.dto.GetCarriagesResponse;
import pl.edu.pg.eti.train_a.dto.PostCarriageRequest;

import java.util.Map;
import java.util.UUID;

public interface CarriageController {
    @GetMapping("api/carriages")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetCarriagesResponse getCarriages();

    @PostMapping("api/carriage")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Map<String, UUID> postCarriage(@RequestBody PostCarriageRequest request);
}
