package pissir.watermanager.mqtt.subscriber;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Service
public class Subscriber {
	
	private String url = "";
	private static final Logger logger = LogManager.getLogger(Subscriber.class.getName());
	private MqttClient mqttClient;
	private SubscribeCallback callback;
	
	
	public Subscriber() {
		try {
			
			String username = "";
			String password = "";
			
			try (FileReader reader = new FileReader(System.getProperty("user.dir") + "/Config/config.json")) {
				JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
				
				username = jsonObject.get("username").getAsString();
				password = jsonObject.get("password").getAsString();
				url = jsonObject.get("urlMqtt").getAsString();
			} catch (IOException e) {
				logger.error("Impossibile aprire il file di configuraizone");
			}
			
			this.mqttClient = new MqttClient(url, MqttClient.generateClientId());
			char[] pwd = password.toCharArray();
			MqttConnectOptions options = new MqttConnectOptions();
			
			options.setAutomaticReconnect(true);
			this.mqttClient.connect(options);
			options.setUserName(username);
			options.setPassword(pwd);
			
			logger.info("Creating subscriber: username = {}", options.getUserName());
			
			this.callback = new SubscribeCallback();
			this.mqttClient.setCallback(this.callback);
		} catch (MqttException e) {
			logger.error("Impossibile creare un client MQTT");
			
		}
		
		
	}
	
	
	public void subscribe(String topic) throws MqttException {
		logger.info("Subscribing topic: {}", topic);
		
		this.mqttClient.subscribe(topic, (s, mqttMessage) -> {
			this.callback.messageArrived(topic, mqttMessage);
		});
	}
	
}
