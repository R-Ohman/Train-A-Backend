package pl.edu.pg.eti.train_a.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
@Setter
public class CustomResponseStatusException extends ResponseStatusException {
    private final ErrorResponse errorResponse;

    public CustomResponseStatusException(HttpStatus status, String reason, String message) {
        super(status, message);
        this.errorResponse = new ErrorResponse(reason, message);
    }
}