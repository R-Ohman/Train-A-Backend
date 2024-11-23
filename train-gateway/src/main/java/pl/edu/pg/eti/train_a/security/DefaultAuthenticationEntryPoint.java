package pl.edu.pg.eti.train_a.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.server.ServerWebExchange;
import pl.edu.pg.eti.train_a.util.ErrorResponse;
import reactor.core.publisher.Mono;

@Component
public class DefaultAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono.defer(() -> {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().set("Content-Type", "application/json");
            try {
                return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                        .bufferFactory().wrap(new ObjectMapper().writeValueAsBytes(new ErrorResponse("invalidAccessToken", "Access is not granted")))));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}