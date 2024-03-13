package org.example.mqtt.controller;

import jakarta.servlet.http.HttpSession;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt.model.Sensore;
import org.example.mqtt.service.IotConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/iot/v1")
public class IotConnectionController {
	
	private final IotConnectionService iotConnectionService;
	
	
	@Autowired
	public IotConnectionController(IotConnectionService iotConnectionService) {
		this.iotConnectionService = iotConnectionService;
	}
	
	
	@PostMapping(value = "/connection", produces = "application/json")
	public void getConnection(HttpSession httpSession, @RequestBody List<Sensore> sensoreModelList)
			throws MqttException {
		iotConnectionService.getConnection(httpSession, sensoreModelList);
	}
	
}
