package org.example.mqtt.publisher;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Service
public class Publisher {

	private final String url = "tcp://127.0.0.1:1883";
	private MqttClient client;
    private static final Logger logger = LogManager.getLogger(Publisher.class.getName());



	public Publisher() {
		String clientId = MqttClient.generateClientId();

		try {
			this.client = new MqttClient(url, clientId);
			String password = "12345678";
			char[] pwd = password.toCharArray();

			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName("admin");
			options.setPassword(pwd);
			options.setCleanSession(false);

			logger.info("Creating publisher: username={}", options.getUserName());

			options.setWill(this.client.getTopic("home/LWT"), "I'm gone. Bye.".getBytes(), 0, false);

			this.client.connect(options);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}


	public void publish(String topic, String message) throws MqttException {
		MqttMessage mqttMessage = new MqttMessage();

		mqttMessage.setPayload(message.getBytes(StandardCharsets.UTF_8));
		mqttMessage.setQos(1);

		logger.info("Publishing on topic: {}, message: {}", topic, message);

		client.publish(topic, mqttMessage);
	}


}
