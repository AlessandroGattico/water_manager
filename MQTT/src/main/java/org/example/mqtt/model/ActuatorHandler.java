package org.example.mqtt.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt.publisher.Publisher;
import org.example.mqtt.simulazione.Simulazione;
import org.example.mqtt.subscriber.Subscriber;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ActuatorHandler {
	
	private static final Logger logger = LogManager.getLogger(Simulazione.class.getName());
	
	private final Publisher publisher;
	private final Subscriber subscriber;
	private final List<String> subscribed;
	
	
	public ActuatorHandler(Publisher publisher, Subscriber subscriber) {
		this.publisher = publisher;
		this.subscriber = subscriber;
		this.subscribed = new ArrayList<>();
	}
	
	
	public void pub(String topic, String message) throws MqttException {
		try {
			if (topic.contains("ACTUATOR")) {
				this.publisher.publish(topic, message);
				logger.info("Setting ACTUATOR {}, on topic {}", topic, message);
			}
		} catch (
				MqttException e) {
			logger.error("Error publishing message: {}", e.getMessage());
		}
	}
	
}
