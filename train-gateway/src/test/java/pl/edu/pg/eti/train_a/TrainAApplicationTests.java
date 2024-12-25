package pl.edu.pg.eti.train_a;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;

@SpringBootTest
@MockBean(EurekaAutoServiceRegistration.class)
class TrainAApplicationTests {

	@Test
	void contextLoads() {
	}

}
