package org.example.mqtt.subscriber;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;


/**
 * @author Alessandro Gattico
 */
public class SubscribeCallback implements MqttCallback {
	
	@Value("${api.add.misura}")
	private String apiUrl;
	
	
	@Override
	public void connectionLost(Throwable cause) {
	}
	
	
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String misura = new String(message.getPayload());
		RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.postForObject(apiUrl, misura, String.class);
	}
	
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}
	
}