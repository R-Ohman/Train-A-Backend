package pl.edu.pg.eti.train_a.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ReactiveJwtFilter implements WebFilter {
    private final JwtUtil jwtUtil;
    private final ReactiveUserDetailsService userDetailsService;

    public ReactiveJwtFilter(JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String username = jwtUtil.getUsernameFromToken(jwt);

            if (username != null && jwtUtil.validateToken(jwt)) {
                UserDetails userDetails = userDetailsService.findByUsername(username).block();
                SecurityContext context = new SecurityContextImpl(
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                );
                return ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)).stream().map(c ->
                        (Mono.deferContextual(ctx -> chain.filter(exchange)))).findFirst().get();
            }
        }

        return chain.filter(exchange);
    }
}
