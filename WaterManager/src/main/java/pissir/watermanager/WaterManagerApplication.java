package pissir.watermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@SpringBootApplication
@EnableScheduling
public class WaterManagerApplication {
	
	public static void main (String[] args) {
		SpringApplication.run(WaterManagerApplication.class, args);
	}
	
}
