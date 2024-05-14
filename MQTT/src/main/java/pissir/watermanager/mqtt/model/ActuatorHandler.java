package pissir.watermanager.mqtt.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;
import pissir.watermanager.mqtt.publisher.Publisher;
import pissir.watermanager.mqtt.simulazione.Simulazione;
import pissir.watermanager.mqtt.subscriber.Subscriber;

import java.util.ArrayList;
import java.util.List;


@Service
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
	
	
	public void pub(String topic, String message) {
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
	
	
	public void sub(String topic) throws MqttException {
		if (! this.subscribed.contains(topic)) {
			this.subscriber.subscribe(topic);
			this.subscribed.add(topic);
		}
		
	}
	
}
