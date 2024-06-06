package pissir.watermanager.mqtt.simulazione;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pissir.watermanager.mqtt.model.Misura;
import pissir.watermanager.mqtt.publisher.Publisher;
import pissir.watermanager.mqtt.subscriber.Subscriber;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */
@Component
public class Simulazione {
	
	private static final Logger logger = LogManager.getLogger(Simulazione.class.getName());
	
	private final Publisher publisher;
	private final Subscriber subscriber;
	private final List<String> subscribed;
	
	private String apiTopics;
	
	
	public Simulazione() {
		this.subscriber = new Subscriber();
		this.publisher = new Publisher();
		
		/*
		try (FileReader reader = new FileReader(System.getProperty("user.dir") + "/Config/config.json")) {
			JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
			
			apiTopics = jsonObject.get("apiTopics").getAsString();
		} catch (IOException e) {
			logger.error("Impossibile aprire il file di configuraizone");
		}
		 */
		
		this.apiTopics = "http://localhost:8080/api/v1/utils/topics/get/all";
		this.subscribed = new ArrayList<>();
	}
	
	
	@Scheduled(fixedRate = 300000)
	public void scheduleFixedRateTask() throws MqttException, InterruptedException {
		this.start();
	}
	
	
	private void start() throws MqttException, InterruptedException {
		//Thread.sleep(120000);
		
		Gson gson = new Gson();
		RestTemplate restTemplate = new RestTemplate();
		
		logger.info("Inizio simulazione misure");
		
		try {
			String result = restTemplate.getForObject(this.apiTopics, String.class);
			Topics topics = gson.fromJson(result, Topics.class);
			
			for (TopicCreator topic : topics.getTopics()) {
				if (! this.subscribed.contains(topic.getTopic())) {
					this.subscriber.subscribe(topic.getTopic());
					this.subscribed.add(topic.getTopic());
				}
				
				if (topic.getTypeSensore().equals("TEMPERATURA")) {
					String time =
							LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
					double randomValue = new Random().nextDouble() * 50;
					
					BigDecimal bd = new BigDecimal(randomValue).setScale(2, RoundingMode.HALF_UP);
					double roundedValue = bd.doubleValue();
					
					this.publisher.publish(topic.getTopic(),
							gson.toJson(new Misura(roundedValue, time, topic.getIdSensore())));
				} else if (topic.getTypeSensore().equals("UMIDITA")) {
					String time =
							LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
					double randomValue = new Random().nextDouble() * 100;
					
					BigDecimal bd = new BigDecimal(randomValue).setScale(2, RoundingMode.HALF_UP);
					double roundedValue = bd.doubleValue();
					
					this.publisher.publish(topic.getTopic(),
							gson.toJson(new Misura(roundedValue, time, topic.getIdSensore())));
				}
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (HttpClientErrorException.NotFound e) {
			logger.error("Endpoint non trovato: {}", this.apiTopics);
		} catch (RestClientException e) {
			logger.error("Errore durante la chiamata REST: {}", e.getMessage());
		}
	}
	
	
}
