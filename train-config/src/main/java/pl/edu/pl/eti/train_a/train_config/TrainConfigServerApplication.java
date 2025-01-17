package pl.edu.pl.eti.train_a.train_config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class TrainConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainConfigServerApplication.class, args);
	}

}
