package org.example.mqtt.service;

import jakarta.servlet.http.HttpSession;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt.model.Sensore;

import java.util.List;

public interface IotConnectionService {
	
	void getConnection(HttpSession httpSession, List<Sensore> sensoreModelList) throws MqttException;
	
}
