package org.example.mqtt.subscriber;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt.service.IotRegisteredMisuraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemperatureSubscriber {
	
	private final IMqttClient mqttClient;
	private final static String TOPIC = "serra/temperature";
	private final IotRegisteredMisuraService iotRegisteredMisuraService;
	
	
	@Autowired
	public TemperatureSubscriber(IMqttClient mqttClient, IotRegisteredMisuraService iotRegisteredMisuraService) {
		this.mqttClient = mqttClient;
		this.iotRegisteredMisuraService = iotRegisteredMisuraService;
	}
	
	
	public void subscribe() throws MqttException {
		mqttClient.subscribeWithResponse(TOPIC, (topic, message) -> {
			iotRegisteredMisuraService.registeredMisura(message);
		});
	}
	
}
