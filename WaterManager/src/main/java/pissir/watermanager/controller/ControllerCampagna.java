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
import pissir.watermanager.model.utils.cambio.CambioString;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/azienda/campagna")
@RequiredArgsConstructor
public class ControllerCampagna {
	
	private final DAO daoCampagna;
	private final TokenService tokenService;
	public static final Logger logger = LogManager.getLogger(ControllerAdmin.class.getName());
	
	
	@PostMapping(value = "/add")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String addCampagna(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		logger.info("Campagna | add");
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campagna | add | concesso");
			
			Campagna campagna = gson.fromJson(param, Campagna.class);
			
			return gson.toJson(this.daoCampagna.addCampagna(campagna));
		} else {
			logger.info("Campagna | add | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getCampagnaId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		logger.info("Campagna | get | " + id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campagna | get | " + id + " | concesso");
			
			Campagna campagna = this.daoCampagna.getCampagnaId(id);
			
			return gson.toJson(campagna);
		} else {
			logger.info("Campagna | get | " + id + " | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getCampagneAzienda(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		logger.info("Campagna | get | campagne azienda | " + id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campagna | get | campagne azienda | " + id + " | concesso");
			
			HashSet<Campagna> campagne = this.daoCampagna.getCampagnaAzienda(id);
			
			return gson.toJson(campagne);
		} else {
			logger.info("Campagna | get | campagne azienda | " + id + " | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String deleteCampagna(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		logger.info("Campagna | elimina | " + id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campagna | elimina | " + id + " | concesso");
			
			this.daoCampagna.deleteCampagna(id);
			return gson.toJson("OK");
		} else {
			logger.info("Campagna | elimina | " + id + " | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/modifica/nome")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String modificaNome(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		logger.info("Campagna | modifica | nome");
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campagna | modifica | nome | concesso");
			
			CambioString cambio = gson.fromJson(param, CambioString.class);
			
			return gson.toJson(this.daoCampagna.cambiaNomeCampagna(cambio));
		} else {
			logger.info("Campagna | modifica | nome | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
}
