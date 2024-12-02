package pl.edu.pg.eti.train_a.carriage.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.eti.train_a.carriage.entity.Carriage;

import java.util.UUID;

public interface CarriageController {
    @PostMapping("/api/carriage")
    @ResponseStatus(HttpStatus.CREATED)
    void postCarriage(@RequestBody Carriage carriage);

    @PutMapping("/api/carriage")
    @ResponseStatus(HttpStatus.CREATED)
    void putCarriage(@RequestBody Carriage carriage);

    @DeleteMapping("/api/carriage/{carriageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCarriage(@PathVariable UUID carriageId);
}