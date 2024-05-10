package org.example.mqtt.subscriber;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.mqtt.model.Attivazione;
import org.springframework.web.client.RestTemplate;


/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */
public class SubscribeCallback implements MqttCallback {
	
	private static final Logger logger = LogManager.getLogger(SubscribeCallback.class.getName());
	private final String apiUrlSensor = "http://localhost:8080/api/v1/misura/add";
	
	
	@Override
	public void connectionLost(Throwable cause) {
	}
	
	
	@Override
	public void messageArrived(String topic, MqttMessage message) {
		String mex = new String(message.getPayload());
		RestTemplate restTemplate = new RestTemplate();
		
		logger.info("Message arrived: {}, on topic: {}", mex, topic);
		
		if (topic.contains("SENSOR")) {
			restTemplate.postForObject(apiUrlSensor, mex, String.class);
		} else if (topic.contains("ACTUATOR")) {
			Gson gson = new Gson();
			
			Attivazione attivazione = gson.fromJson(mex, Attivazione.class);
			
			logger.info("Attuatore {} impostato su {}", attivazione.getIdAttuatore(), attivazione.isCurrent());
		}
		
	}
	
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}
	
}