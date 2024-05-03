package org.example.mqtt.subscriber;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */
public class Subscriber {
	
	private final String url = "tcp://127.0.0.1:1883";
	
	private MqttClient mqttClient;
	private SubscribeCallback callback;
	
	
	public Subscriber() {
		try {
			this.mqttClient = new MqttClient(url, MqttClient.generateClientId());
			
			String password = "roger";
			char[] pwd = password.toCharArray();
			MqttConnectOptions options = new MqttConnectOptions();
			
			options.setAutomaticReconnect(true);
			this.mqttClient.connect(options);
			options.setUserName("roger");
			options.setPassword(pwd);
			
			this.callback = new SubscribeCallback();
			this.mqttClient.setCallback(this.callback);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void subscribe(String topic) throws MqttException {
		this.mqttClient.subscribe(topic, (s, mqttMessage) -> {
			this.callback.messageArrived(topic, mqttMessage);
		});
	}
	
}
