package pl.edu.pg.eti.train_a;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class TrainAApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainAApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(
            RouteLocatorBuilder builder,
            @Value("${train.user.url}") String userUrl,
            @Value("${train.railway.url}") String railwayUrl,
            @Value("${train.gateway.host}") String host
    ) {
        return builder
                .routes()
                .route("users", route -> route
                        .host(host)
                        .and()
                        .path(
                                "/api/profile",
                                "/api/profile/password",
                                "/api/users",
                                "/api/logout",
                                "/api/signin",
                                "/api/signup",
                                "/api/order",
                                "/api/order/**"
                        )
                        .uri(userUrl)
                )
                .route("railways", route -> route
                        .host(host)
                        .and()
                        .path(
                                "/api/search",
                                "/api/search/**",
                                "/api/ride",
                                "/api/station",
                                "/api/carriage",
                                "/api/route/**",
                                "/api/carriage/**",
                                "/api/station/**"
                        )
                        .uri(railwayUrl)
                )
                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {

        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        corsConfig.addAllowedHeader("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
