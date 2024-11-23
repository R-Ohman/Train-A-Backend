package pl.edu.pg.eti.train_a;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import pl.edu.pg.eti.train_a.security.ReactiveJwtFilter;

@SpringBootApplication
public class TrainAApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainAApplication.class, args);
	}

	@Bean
	public RouteLocator routeLocator(
			RouteLocatorBuilder builder,
			ReactiveJwtFilter jwtFilter,
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
								"/api/route",
								"/api/carriage",
								"/api/route/**",
								"/api/carriage/**",
								"/api/station/**"
						)
						.uri(railwayUrl)
				)
				.build();
	}
}
