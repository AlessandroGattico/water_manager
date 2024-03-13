package org.example.mqtt.publisher;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;


/**
 * @author Alessandro Gattico
 */
public class Publisher {
	
	@Value("${mqtt.connection.url}")
	private String url;
	private MqttClient client;
	
	
	public Publisher() {
		String clientId = MqttClient.generateClientId();
		
		try {
			this.client = new MqttClient(url, clientId);
			String password = "password1";
			char[] pwd = password.toCharArray();
			
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName("upouser1");
			options.setPassword(pwd);
			options.setCleanSession(false);
			
			options.setWill(this.client.getTopic("home/LWT"), "I'm gone. Bye.".getBytes(), 0, false);
			
			this.client.connect(options);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	
	public void publish(String topic, String message) throws MqttException {
		MqttTopic publisherTopic = this.client.getTopic(topic);
		publisherTopic.publish(new MqttMessage(message.getBytes(StandardCharsets.UTF_8)));
	}
	
}
