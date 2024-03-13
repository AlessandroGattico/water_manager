package org.example.mqtt.configuration;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration {
	
	@Bean
	public WebMvcConfigurer corsConfiguration() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
				WebMvcConfigurer.super.addCorsMappings(registry);
			}
		};
	}
	
	
	@Bean
	public IMqttClient iMqttClient() throws MqttException {
		IMqttClient mqttClient = new MqttClient("tcp://test.mosquitto.org:1883", MqttClient.generateClientId());
		mqttClient.connect(mqttConnectOptions());
		return mqttClient;
	}
	
	
	@Bean
	public MqttConnectOptions mqttConnectOptions() {
		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		mqttConnectOptions.setAutomaticReconnect(true);
		mqttConnectOptions.setServerURIs(new String[]{"tcp://test.mosquitto.org:1883"});
		return mqttConnectOptions;
	}
	
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}
	
}
