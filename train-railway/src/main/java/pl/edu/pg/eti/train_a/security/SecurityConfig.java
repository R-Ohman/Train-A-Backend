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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.edu.pg.eti.train_a.user.entity.UserRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;
    private final AccessDeniedHandler accessDeniedHandler;


    @Autowired
    public SecurityConfig(
            JwtRequestFilter jwtRequestFilter,
            AccessDeniedHandler accessDeniedHandler
    ) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, DefaultAuthenticationEntryPoint authenticationEntryPoint) throws Exception {

        http.securityMatcher("/api/**").authorizeHttpRequests(rmr -> rmr
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/users",
                                "/api/route/{id}"
                        ).hasRole(UserRole.MANAGER.getValue())
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/carriage/{code}",
                                "/api/station/{id}",
                                "/api/route/{id}",
                                "/api/route/{routeId}/ride/{rideId}"
                        ).hasRole(UserRole.MANAGER.getValue())
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/carriage/{code}",
                                "/api/route/{id}",
                                "/api/route/{routeId}/ride/{rideId}"
                        ).hasRole(UserRole.MANAGER.getValue())
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/carriage",
                                "/api/station",
                                "/api/route",
                                "/api/route/{routeId}/ride"
                        ).hasRole(UserRole.MANAGER.getValue())
                        .anyRequest().permitAll()
                ).httpBasic(httpbc -> httpbc
                        .authenticationEntryPoint(authenticationEntryPoint)
                ).sessionManagement(smc -> smc
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ehc -> ehc
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}