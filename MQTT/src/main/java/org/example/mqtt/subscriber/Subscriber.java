package org.example.mqtt.subscriber;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Service
public class Subscriber {
	
	private final String url = "tcp://127.0.0.1:1883";
	private static final Logger logger = LogManager.getLogger(Subscriber.class.getName());
	private MqttClient mqttClient;
	private SubscribeCallback callback;
	
	
	public Subscriber() {
		try {
			this.mqttClient = new MqttClient(url, MqttClient.generateClientId());
			
			String password = "password";
			char[] pwd = password.toCharArray();
			MqttConnectOptions options = new MqttConnectOptions();
			
			options.setAutomaticReconnect(true);
			this.mqttClient.connect(options);
			options.setUserName("username");
			options.setPassword(pwd);
			
			logger.info("Creating subscriber: username={}", options.getUserName());
			
			this.callback = new SubscribeCallback();
			this.mqttClient.setCallback(this.callback);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void subscribe(String topic) throws MqttException {
		logger.info("Subscribing topic: {}", topic);
		
		this.mqttClient.subscribe(topic, (s, mqttMessage) -> {
			this.callback.messageArrived(topic, mqttMessage);
		});
	}
	
}
