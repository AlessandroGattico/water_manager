package pissir.watermanager.mqtt.publisher;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Service
public class Publisher {
	
	private MqttClient client;
	private static final Logger logger = LogManager.getLogger(Publisher.class.getName());
	
	
	public Publisher() {
		String clientId = MqttClient.generateClientId();
		
		try {
			String username = "";
			String password = "";
			String url = "";
			
			try (FileReader reader = new FileReader(System.getProperty("user.dir") + "/Config/config.json")) {
				JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
				
				username = jsonObject.get("username").getAsString();
				password = jsonObject.get("password").getAsString();
				url = jsonObject.get("urlMqtt").getAsString();
			} catch (IOException e) {
				logger.error("Impossibile aprire il file di configuraizone");
			}
			
			this.client = new MqttClient(url, clientId);
			char[] pwd = password.toCharArray();
			
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(username);
			options.setPassword(pwd);
			options.setCleanSession(false);
			
			logger.info("Creating publisher: username = {}", options.getUserName());
			
			options.setWill(this.client.getTopic("home/LWT"), "I'm gone. Bye.".getBytes(), 0, false);
			
			this.client.connect(options);
		} catch (MqttException e) {
			logger.error("Impossibile creare un client MQTT");
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
