package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Coltivazione;
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
@RequestMapping("/api/v1/azienda/coltivazione")
@RequiredArgsConstructor
public class ControllerColtivazione {
	
	private final DAO daoColtivazione;
	private final TokenService tokenService;
	private static final Logger logger = LogManager.getLogger(ControllerColtivazione.class.getName());
	
	
	@PostMapping(value = "/add")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String addColtivazione(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Coltivazione | add | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Coltivazione | add | {} | concesso", param);
			
			Coltivazione coltivazione = gson.fromJson(param, Coltivazione.class);
			
			return gson.toJson(this.daoColtivazione.addColtivazione(coltivazione));
		} else {
			logger.info("Coltivazione | add | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getColtivazioneId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Coltivazione | get | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Coltivazione | get | {} | concesso", id);
			
			Coltivazione coltivazione = this.daoColtivazione.getColtivazioneId(id);
			
			return gson.toJson(coltivazione);
		} else {
			logger.info("Coltivazione | get | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getColtivazioniCampo(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Coltivazione | get coltivazioni campo | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Coltivazione | get coltivazioni campo | {} | concesso", id);
			
			HashSet<Coltivazione> coltivazioni = this.daoColtivazione.getColtivazioniCampo(id);
			
			return gson.toJson(coltivazioni);
		} else {
			logger.info("Coltivazione | get coltivazioni campo | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String deleteColtivazione(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Coltivazione | elimina | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Coltivazione | elimina | {} | concesso", id);
			
			this.daoColtivazione.deleteColtivazione(id);
			
			return gson.toJson("OK");
		} else {
			logger.info("Coltivazione | elimina | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
}
