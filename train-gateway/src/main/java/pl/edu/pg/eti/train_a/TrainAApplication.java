package pl.edu.pg.eti.train_a;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrainAApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainAApplication.class, args);
	}

	@Bean
	public ObjectWriter objectWriter() {
		return new ObjectMapper()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
				.writer(new DefaultPrettyPrinter()
						.withArrayIndenter(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE));
	}
}
