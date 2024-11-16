package pl.edu.pg.eti.train_a.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.edu.pg.eti.train_a.user.entity.UserRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**").authorizeHttpRequests(rmr -> rmr
                .requestMatchers(
                        "/api/profile",
                        "/api/profile/password",
                        "/api/logout"
                ).authenticated()
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
                .authenticationEntryPoint(authenticationEntryPoint())
        ).sessionManagement(smc -> smc
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public HttpStatusEntryPoint authenticationEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}