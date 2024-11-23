package pl.edu.pg.eti.train_a.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.server.ServerWebExchange;
import pl.edu.pg.eti.train_a.util.ErrorResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class DefaultAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return Mono.defer(() -> {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().set("Content-Type", "application/json");
            try {
                return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                        .bufferFactory().wrap(new ObjectMapper().writeValueAsBytes(new ErrorResponse("invalidAccessToken", "Access is not granted")))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
