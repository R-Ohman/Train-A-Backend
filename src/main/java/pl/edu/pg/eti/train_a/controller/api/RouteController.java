package pl.edu.pg.eti.train_a.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.pg.eti.train_a.dto.GetRoutesResponse;

public interface RouteController {
    @GetMapping("api/routes")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRoutesResponse getRoutes();
}
