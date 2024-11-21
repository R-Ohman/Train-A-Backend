package pl.edu.pg.eti.train_a.carriage.controller.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.carriage.dto.GetCarriagesResponse;
import pl.edu.pg.eti.train_a.carriage.dto.PostCarriageRequest;
import pl.edu.pg.eti.train_a.carriage.dto.PutCarriageRequest;

import java.util.Map;
import java.util.UUID;

public interface CarriageController {
    @GetMapping("api/carriage")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetCarriagesResponse getCarriages();

    @PostMapping("api/carriage")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Map<String, UUID> postCarriage(@RequestBody @Valid PostCarriageRequest request);

    @PutMapping("/api/carriage/{code}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    Map<String, UUID> putCarriage(@PathVariable UUID code, @RequestBody @Valid PutCarriageRequest request);

    @DeleteMapping("/api/carriage/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCarriage(@PathVariable UUID code);
}
