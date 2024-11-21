package pl.edu.pg.eti.train_a.advice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    String reason;
    String message;
}
