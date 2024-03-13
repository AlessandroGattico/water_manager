package org.example.mqtt.task;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.mqtt.subscriber.HumiditySubscriber;
import org.example.mqtt.subscriber.TemperatureSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SubscribeTask {
	
	private final TemperatureSubscriber temperatureSubscriber;
	private final HumiditySubscriber humiditySubscriber;
	
	@Autowired
	public SubscribeTask(TemperatureSubscriber temperatureSubscriber, HumiditySubscriber humiditySubscriber) {
		this.temperatureSubscriber = temperatureSubscriber;
		this.humiditySubscriber = humiditySubscriber;
	}
	
	
	@Scheduled(cron = "1 * * * * ?")
	public void runTemperatureSubscribe() throws MqttException {
		temperatureSubscriber.subscribe();
	}
	
	
	@Scheduled(cron = "2 * * * * ?")
	public void runHumiditySubscribe() throws MqttException {
		humiditySubscriber.subscribe();
	}
	
}
