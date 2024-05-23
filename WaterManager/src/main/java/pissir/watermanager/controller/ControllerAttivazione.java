package pissir.watermanager.controller;


import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Attivazione;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;
import pissir.watermanager.security.utils.TokenCheck;

import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@RestController
@RequestMapping("/api/v1/azienda/attivazione")
@RequiredArgsConstructor
public class ControllerAttivazione {
	
	private final DAO daoAttivazioni;
	private final TokenService tokenService;
	private static final Logger logger = LogManager.getLogger(ControllerAttivazione.class.getName());
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getAttivazioneId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Attivazione | get | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Attivazione | get | {} | concesso", id);
			
			Attivazione attivazione = this.daoAttivazioni.getAttivazioneId(id);
			
			return gson.toJson(attivazione);
		} else {
			logger.info("Attivazione | get | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/getAttuatore/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getAttivazioniAttuatore(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Attivazione | get attivazioni attuatore | {}", id);
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Attivazione | get attivazioni attuatore | {} | concesso", id);
			
			HashSet<Attivazione> attivazioni = this.daoAttivazioni.getAttivazioniAttuatore(id);
			
			return gson.toJson(attivazioni);
		} else {
			logger.info("Attivazione | get attivazioni attuatore | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/add")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String addAttivazione(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Attivazione | add | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Attivazione | add | {} | concesso", param);
			
			Attivazione attivazione = gson.fromJson(param, Attivazione.class);
			
			RestTemplate restTemplate = new RestTemplate();
			String topic = this.daoAttivazioni.getTopicAttuatore(attivazione.getIdAttuatore());
			String apiUrlActuator = "http://localhost:8081/api/v1/MQTT/run/attuatore/" + topic;
			
			this.daoAttivazioni.addAttivazione(attivazione);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(gson.toJson(attivazione), headers);
			
			logger.info("Calling API at: {}", apiUrlActuator);
			logger.info("With body: {}", entity.getBody());
			
			restTemplate.postForObject(apiUrlActuator, entity, String.class);
			
			return gson.toJson("");
		} else {
			logger.info("Attivazione | add | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String deleteAttivazione(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Attivazione | delete | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Attivazione | delete | {} | concesso", id);
			
			this.daoAttivazioni.deleteAttivazione(id);
			
			return gson.toJson("OK");
		} else {
			logger.info("Attivazione | delete | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
}
