package pl.edu.pg.eti.train_a;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TrainUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainUserApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(@Value("${train.railway.url}") String baseUrl) {
		return new RestTemplateBuilder().rootUri(baseUrl).build();
	}
}
