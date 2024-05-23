package pissir.watermanager.mqtt.publisher;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

public class Publisher {
	
	private MqttClient client;
	private static final Logger logger = LogManager.getLogger(Publisher.class.getName());
	
	
	public Publisher() {
		String clientId = MqttClient.generateClientId();
		
		try {
			String username = "username";
			String password = "password";
			String url = "tcp://127.0.0.1:1883";
			
			/*
			try (FileReader reader = new FileReader("/Config/config.json")) {
				JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
				
				if (jsonObject.has("username") && jsonObject.has("password") && jsonObject.has("urlMqtt")) {
					username = jsonObject.get("username").getAsString();
					password = jsonObject.get("password").getAsString();
					url = jsonObject.get("urlMqtt").getAsString();
				} else {
					logger.error("File di configurazione mancante di username, password o urlMqtt");
					throw new IllegalArgumentException("Configurazione incompleta nel file JSON");
				}
			} catch (IOException e) {
				logger.error("Impossibile aprire il file di configurazione", e);
				throw new RuntimeException("Errore di I/O durante la lettura del file di configurazione", e);
			}
			 */
			
			this.client = new MqttClient(url, clientId);
			char[] pwd = password.toCharArray();
			
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(username);
			options.setPassword(pwd);
			options.setCleanSession(false);
			
			logger.info("Creo publisher con username = {}", options.getUserName());
			
			options.setWill(this.client.getTopic("home/LWT"), "I'm gone. Bye.".getBytes(), 0, false);
			
			this.client.connect(options);
		} catch (MqttException e) {
			logger.error("Impossibile creare un client MQTT", e);
			throw new RuntimeException("Errore durante la creazione del client MQTT", e);
		}
	}
	
	
	public void publish(String topic, String message) throws MqttException {
		MqttMessage mqttMessage = new MqttMessage();
		
		mqttMessage.setPayload(message.getBytes(StandardCharsets.UTF_8));
		mqttMessage.setQos(1);
		
		logger.info("Pubblico con topic: {}, messaggio: {}", topic, message);
		
		client.publish(topic, mqttMessage);
	}
	
}
