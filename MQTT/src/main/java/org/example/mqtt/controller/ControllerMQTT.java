package org.example.mqtt.controller;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt.model.ActuatorHandler;
import org.springframework.web.bind.annotation.*;


/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/MQTT")
public class ControllerMQTT {
	
	private static final Logger logger = LogManager.getLogger(ControllerMQTT.class.getName());
	private final ActuatorHandler actuatorHandler;
	
	
	@PostMapping(value = "/run/attuatore/{topic}")
	public void runActuator(@RequestBody String body, @PathVariable String topic) throws MqttException {
		this.actuatorHandler.sub(topic);
		this.actuatorHandler.pub(topic, body);
	}
	
}
