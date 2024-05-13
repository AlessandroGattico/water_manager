package org.example.mqtt;


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
public class MqttApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MqttApplication.class, args);
	}
	
}
