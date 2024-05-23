package pissir.watermanager.mqtt.controller;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.mqtt.model.ActuatorHandler;


/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@RestController
@RequestMapping("/api/v1/MQTT")
@CrossOrigin("*")
@AllArgsConstructor
public class ControllerMQTT {
	
	private static final Logger logger = LogManager.getLogger(ControllerMQTT.class.getName());
	private final ActuatorHandler actuatorHandler;
	
	
	public ControllerMQTT() {
		this.actuatorHandler = new ActuatorHandler();
	}
	
	
	@PostMapping(value = "/run/attuatore/{topic}")
	public void runActuator(@RequestBody String body, @PathVariable String topic) throws MqttException {
		logger.info("Ricevuta richiesta con topic: {} e body: {}", topic, body);
		
		this.actuatorHandler.sub(topic);
		this.actuatorHandler.pub(topic, body);
	}
	
}
