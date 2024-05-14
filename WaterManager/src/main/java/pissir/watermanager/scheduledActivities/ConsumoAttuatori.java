package pissir.watermanager.scheduledActivities;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

@Service
public class ConsumoAttuatori {
	
	private final DAO dao;
	
	
	public ConsumoAttuatori(DAO dao) {
		this.dao = dao;
	}
	
	
	public void aggiornaConsumo() {
		Gson gson = new Gson();
		
		HashSet<Azienda> aziende = this.dao.getAziende();
		
		for (Azienda azienda : aziende) {
			for (Campagna campagna : azienda.getCampagne()) {
				for (Campo campo : campagna.getCampi()) {
					HashSet<Attuatore> attuatoriAttivi = this.dao.getAttuatoriAttiviCampo(campo.getId());
					
					Double consumo = attuatoriAttivi.size() * 10.0;
					RisorsaIdrica ultimaRisorsa = this.dao.ultimaRisorsaAzienda(azienda.getId());
					Double dispAzienda = ultimaRisorsa.getDisponibilita() - consumo;
					
					if (dispAzienda <= 0.0) {
						dispAzienda = 0.0;
						
						this.dao.addRisorsaAzienda(new RisorsaIdrica(
								LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
								dispAzienda, ultimaRisorsa.getConsumo() + consumo, azienda.getId()));
						
						RestTemplate restTemplate = new RestTemplate();
						
						for (Attuatore attuatore : attuatoriAttivi) {
							String topic = this.dao.getTopicAttuatore(attuatore.getId());
							
							Attivazione attivazione = new Attivazione(false,
									LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
									attuatore.getId());
							
							String apiUrlActuator = "http://localhost:8081/api/v1/MQTT/run/attuatore/" + topic;
							
							this.dao.addAttivazione(attivazione);
							
							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.APPLICATION_JSON);
							HttpEntity<String> entity = new HttpEntity<>(gson.toJson(attivazione), headers);
							
							restTemplate.postForObject(apiUrlActuator, entity, String.class);
						}
					} else {
						this.dao.addRisorsaAzienda(new RisorsaIdrica(
								LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
								dispAzienda, ultimaRisorsa.getConsumo() + consumo, azienda.getId()));
					}
				}
			}
		}
	}
	
}
