package org.example.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt.publisher.Publisher;
import org.example.mqtt.simulazione.Simulazione;
import org.example.mqtt.subscriber.Subscriber;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@SpringBootApplication
public class MqttApplication {
	
	public static void main(String[] args) throws MqttException {
		SpringApplication.run(MqttApplication.class, args);
		Simulazione simulazione = new Simulazione(new Publisher(), new Subscriber());
		
		simulazione.start();
	}
	
}
