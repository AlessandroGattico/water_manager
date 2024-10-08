package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Campagna;
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
@RequestMapping("/api/v1/azienda/campagna")
@RequiredArgsConstructor
public class ControllerCampagna {
	
	private final DAO daoCampagna;
	private final TokenService tokenService;
	private static final Logger logger = LogManager.getLogger(ControllerCampagna.class.getName());
	
	
	@PostMapping(value = "/add")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String addCampagna(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Campagna | add | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campagna | add | {} | concesso", param);
			
			Campagna campagna = gson.fromJson(param, Campagna.class);
			
			return gson.toJson(this.daoCampagna.addCampagna(campagna));
		} else {
			logger.info("Campagna | add | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getCampagnaId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Campagna | get | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campagna | get | {} | concesso", id);
			
			Campagna campagna = this.daoCampagna.getCampagnaId(id);
			
			return gson.toJson(campagna);
		} else {
			logger.info("Campagna | get | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getCampagneAzienda(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Campagna | get | campagne azienda | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campagna | get | campagne azienda | {} | concesso", id);
			
			HashSet<Campagna> campagne = this.daoCampagna.getCampagnaAzienda(id);
			
			return gson.toJson(campagne);
		} else {
			logger.info("Campagna | get | campagne azienda | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String deleteCampagna(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Campagna | elimina | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campagna | elimina | {} | concesso", id);
			
			this.daoCampagna.deleteCampagna(id);
			
			return gson.toJson("OK");
		} else {
			logger.info("Campagna | elimina | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
}
