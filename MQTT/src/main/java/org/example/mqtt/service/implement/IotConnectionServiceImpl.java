package org.example.mqtt.service.implement;

import jakarta.servlet.http.HttpSession;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt.model.SensorType;
import org.example.mqtt.model.Sensore;
import org.example.mqtt.service.IotConnectionService;
import org.example.mqtt.shared.SensoreInfos;
import org.example.mqtt.task.PublishTask;
import org.example.mqtt.task.SubscribeTask;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class IotConnectionServiceImpl implements IotConnectionService {
	
	private final PublishTask publishTask;
	private final SubscribeTask subscribeTask;
	
	
	public IotConnectionServiceImpl(PublishTask publishTask, SubscribeTask subscribeTask) {
		this.publishTask = publishTask;
		this.subscribeTask = subscribeTask;
	}
	
	
	@Override
	public void getConnection(HttpSession httpSession, List<Sensore> sensoreModelList) throws MqttException {
		Map<SensorType, List<Sensore>> sensoreClassificationBySerra =
				sensoreModelList.stream().collect(groupingBy(Sensore :: getType));
		SensoreInfos.INSTANCE.setSensoreClassificationByType(sensoreClassificationBySerra);
		
		publishTask.runHumidityPublish();
		publishTask.runTemperaturePublish();
		
		subscribeTask.runTemperatureSubscribe();
		subscribeTask.runHumiditySubscribe();
	}
	
}
