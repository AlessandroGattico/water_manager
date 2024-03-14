package org.example.mqtt.publisher;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;


/**
 * @author Alessandro Gattico
 */
public class Publisher {

	private final String url = "tcp://127.0.0.1:1883";
	private MqttClient client;


	public Publisher() {
		String clientId = MqttClient.generateClientId();

		try {
			this.client = new MqttClient(url, clientId);
			String password = "roger";
			char[] pwd = password.toCharArray();

			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName("roger");
			options.setPassword(pwd);
			options.setCleanSession(false);

			options.setWill(this.client.getTopic("sensore/LWT"), "I'm gone. Bye.".getBytes(), 0, false);

			this.client.connect(options);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}


	public void publish(String topic, String message)
			throws MqttException {
		this.client.publish(topic, new MqttMessage(message.getBytes(StandardCharsets.UTF_8)));
	}

}
