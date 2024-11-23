package pl.edu.pg.eti.train_a.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ErrorResponse {
    private Error error;

    public ErrorResponse(String reason, String message) {
        this.error = new Error(reason, message);
    }

    @Data
    @AllArgsConstructor
    private static class Error {
        private String reason;
        private String message;
    }
}
