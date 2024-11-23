package pl.edu.pg.eti.train_a.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.WebFilter;
import pl.edu.pg.eti.train_a.user.entity.UserRole;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private final WebFilter jwtFilter;
    private final ServerAuthenticationEntryPoint authenticationEntryPoint;
    private final ServerAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(
            ReactiveJwtFilter jwtFilter,
            ServerAuthenticationEntryPoint authenticationEntryPoint,
            ServerAccessDeniedHandler accessDeniedHandler
    ) {
        this.jwtFilter = jwtFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(
                                HttpMethod.GET,
                                "/api/users",
                                "/api/routes/{id}"
                        ).hasRole(UserRole.MANAGER.getValue())
                        .pathMatchers(
                                HttpMethod.DELETE,
                                "/api/carriage/{code}",
                                "/api/station/{id}",
                                "/api/route/{id}",
                                "/api/route/{routeId}/ride/{rideId}"
                        ).hasRole(UserRole.MANAGER.getValue())
                        .pathMatchers(
                                HttpMethod.PUT,
                                "/api/carriage/{code}",
                                "/api/route/{id}",
                                "/api/route/{routeId}/ride/{rideId}"
                        ).hasRole(UserRole.MANAGER.getValue())
                        .pathMatchers(
                                HttpMethod.POST,
                                "/api/carriage",
                                "/api/station",
                                "/api/route",
                                "/api/route/{routeId}/ride"
                        ).hasRole(UserRole.MANAGER.getValue())
                        .pathMatchers(
                                "/api/order",
                                "/api/order/*",
                                "/api/profile",
                                "/api/profile/*",
                                "/api/logout"
                        ).authenticated()
                        .anyExchange().permitAll()
                )
                .httpBasic(httpbc -> httpbc
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .exceptionHandling(ehc -> ehc
                        .accessDeniedHandler(accessDeniedHandler)
                );

        http.addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}