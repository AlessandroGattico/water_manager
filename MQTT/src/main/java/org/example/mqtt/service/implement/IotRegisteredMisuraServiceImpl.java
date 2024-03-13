package org.example.mqtt.service.implement;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.mqtt.service.IotRegisteredMisuraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class IotRegisteredMisuraServiceImpl implements IotRegisteredMisuraService {
	
	@Value("${api.add.misura}")
	private String apiUrl;
	private final RestTemplate restTemplate;
	
	
	@Autowired
	public IotRegisteredMisuraServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	
	@Override
	public void registeredMisura(MqttMessage message) {
		String misura = new String(message.getPayload());
		restTemplate.postForEntity(apiUrl, misura, String.class);
	}
	
}
