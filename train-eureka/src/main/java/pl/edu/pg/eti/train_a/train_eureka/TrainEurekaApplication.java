package pl.edu.pg.eti.train_a.train_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TrainEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainEurekaApplication.class, args);
	}

}
