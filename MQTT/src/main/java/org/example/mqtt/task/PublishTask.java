package org.example.mqtt.task;

import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt.model.Misura;
import org.example.mqtt.model.Sensore;
import org.example.mqtt.publisher.HumidityPublisher;
import org.example.mqtt.publisher.TemperaturePublisher;
import org.example.mqtt.shared.SensoreInfos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class PublishTask {
	
	private final TemperaturePublisher temperaturePublisher;
	private final HumidityPublisher humidityPublisher;
	
	
	@Autowired
	public PublishTask(TemperaturePublisher temperaturePublisher, HumidityPublisher humidityPublisher) {
		this.temperaturePublisher = temperaturePublisher;
		this.humidityPublisher = humidityPublisher;
	}
	
	
	@Scheduled(cron = "2 * * * * ?")
	public void runTemperaturePublish() {
		Map<Integer, Integer> allReadyPublish = new HashMap<>();
		
		List<Sensore> temperaturaSensore = SensoreInfos.INSTANCE.getSensoreClassificationByType().values().stream()
				.flatMap(entryByType -> entryByType.stream()
						.filter(sensoreModel -> Objects.equals(sensoreModel.getType(), "TEMPERATURA")))
				.toList();
		
		publishEvent(allReadyPublish, temperaturaSensore, "temperature");
	}
	
	
	@Scheduled(cron = "1 * * * * ?")
	public void runHumidityPublish() {
		Map<Integer, Integer> allReadyPublish = new HashMap<>();
		
		List<Sensore> umiditaSensore = SensoreInfos.INSTANCE.getSensoreClassificationByType().values().stream()
				.flatMap(entryByType -> entryByType.stream()
						.filter(sensoreModel -> Objects.equals(sensoreModel.getType(), "UMIDITA")))
				.toList();
		
		publishEvent(allReadyPublish, umiditaSensore, "humidity");
	}
	
	
	private void publishEvent(Map<Integer, Integer> allReadyPublish, List<Sensore> sensoreModels, String type) {
		Gson gson = new Gson();
		
		switch (type) {
			case "humidity" -> sensoreModels.forEach(sensore -> {
				try {
					if (allReadyPublish.containsKey(sensore.getId())) {
						if (allReadyPublish.get(sensore.getId()) <= 4) {
							String time =
									LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
							humidityPublisher.publish(
									gson.toJson(new Misura(new Random().nextDouble(100), time, sensore.getId())));
							allReadyPublish.put(sensore.getId(), (allReadyPublish.get(sensore.getId()) + 1));
						}
					} else {
						String time =
								LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
						humidityPublisher.publish(
								gson.toJson(new Misura(new Random().nextDouble(100), time, sensore.getId())));
						allReadyPublish.put(sensore.getId(), 1);
					}
				} catch (MqttException e) {
					throw new RuntimeException(e);
				}
			});
			case "temperature" -> sensoreModels.forEach(sensore -> {
				try {
					if (allReadyPublish.containsKey(sensore.getId())) {
						if (allReadyPublish.get(sensore.getId()) <= 4) {
							String time =
									LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
							temperaturePublisher.publish(
									gson.toJson(new Misura(new Random().nextDouble(50), time, sensore.getId())));
							allReadyPublish.put(sensore.getId(), (allReadyPublish.get(sensore.getId()) + 1));
						}
					} else {
						String time =
								LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
						humidityPublisher.publish(
								gson.toJson(new Misura(new Random().nextDouble(50), time, sensore.getId())));
						allReadyPublish.put(sensore.getId(), 1);
					}
				} catch (MqttException e) {
					throw new RuntimeException(e);
				}
			});
		}
	}
	
}
