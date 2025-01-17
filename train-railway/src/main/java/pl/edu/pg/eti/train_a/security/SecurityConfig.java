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
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
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
    public SecurityFilterChain filterChain(HttpSecurity http, DefaultAuthenticationEntryPoint authenticationEntryPoint, MvcRequestMatcher.Builder mvc) throws Exception {
        http.securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> auth
                        // Permit all event requests
                        .requestMatchers(mvc.pattern("/api/event/**"))
                            .permitAll()

                        // Permit all GET requests for anyone except specific ones
                        .requestMatchers(mvc.pattern("/api/route/{id}"))
                            .hasRole(UserRole.MANAGER.getValue())
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "**"))
                            .permitAll()

                        // Permit all PUT, POST, DELETE requests for MANAGER only
                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "**"))
                            .hasRole(UserRole.MANAGER.getValue())
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "**"))
                            .hasRole(UserRole.MANAGER.getValue())
                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "**"))
                            .hasRole(UserRole.MANAGER.getValue())

                        // Deny all other requests
                        .anyRequest()
                            .denyAll()
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

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
}