package pissir.watermanager.mqtt.subscriber;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

public class Subscriber {
	
	private static final Logger logger = LogManager.getLogger(Subscriber.class.getName());
	private MqttClient mqttClient;
	private SubscribeCallback callback;
	
	
	public Subscriber() {
		try {
			String username, password, url;
			
			/*
			JsonObject config = ConfigLoader.getConfigJsonObject("config.json");
			
			if (config.has("username") && config.has("password") && config.has("urlMqtt")) {
				username = config.get("username").getAsString();
				password = config.get("password").getAsString();
				url = config.get("urlMqtt").getAsString();
			} else {
				logger.error("File di configurazione mancante di username, password o urlMqtt");
				throw new IllegalArgumentException("Configurazione incompleta nel file JSON");
			}
			*/
			
			username = "username";
			password = "password";
			url = "tcp://127.0.0.1:1883";
			
			
			this.mqttClient = new MqttClient(url, MqttClient.generateClientId());
			char[] pwd = password.toCharArray();
			MqttConnectOptions options = new MqttConnectOptions();
			
			options.setAutomaticReconnect(true);
			this.mqttClient.connect(options);
			options.setUserName(username);
			options.setPassword(pwd);
			
			logger.info("Creazione subscriber con username = {}", options.getUserName());
			
			this.callback = new SubscribeCallback();
			this.mqttClient.setCallback(this.callback);
		} catch (MqttException e) {
			logger.error("Impossibile creare un client MQTT");
			
		}
		
		
	}
	
	
	public void subscribe(String topic) throws MqttException {
		logger.info("Subscribing sul topic: {}", topic);
		
		this.mqttClient.subscribe(topic, (s, mqttMessage) -> {
			this.callback.messageArrived(topic, mqttMessage);
		});
	}
	
}
