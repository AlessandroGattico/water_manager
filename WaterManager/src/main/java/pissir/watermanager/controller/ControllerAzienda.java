package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Azienda;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.model.utils.cambio.CambioString;
import pissir.watermanager.security.services.TokenService;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/azienda")
@RequiredArgsConstructor
public class ControllerAzienda {
	
	private final DAO daoAzienda;
	private final TokenService tokenService;
	public static final Logger logger = LogManager.getLogger(ControllerAdmin.class.getName());
	
	
	@PostMapping(value = "/add")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String addAzienda(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		logger.info("Azienda | add");
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Azienda | add | concesso");
			
			Azienda azienda = gson.fromJson(param, Azienda.class);
			
			return gson.toJson((this.daoAzienda.addAzienda(azienda)));
		} else {
			logger.info("Azienda | add | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getAziendaId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		logger.info("Azienda | get | " + id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Azienda | get | " + id + " | concesso");
			
			Azienda azienda = this.daoAzienda.getAziendaId(id);
			
			if (azienda != null) {
				return gson.toJson(azienda);
			} else {
				return null;
			}
		} else {
			logger.info("Azienda | get | " + id + " | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/gestore/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getAziendaGa(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		logger.info("Azienda | get | azienda gestore | " + id + " | concesso");
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Azienda | get | azienda gestore | " + id + " | concesso");
			
			Azienda azienda = this.daoAzienda.getAziendaGestore(id);
			
			if (azienda != null) {
				return gson.toJson(azienda);
			} else {
				return null;
			}
		} else {
			logger.info("Azienda | get | azienda gestore | " + id + " | concesso");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String deleteAzienda(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		logger.info("Azienda | elimina | " + id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Azienda | elimina | " + id + " | concesso");
			
			this.daoAzienda.deleteAzienda(id);
			
			return gson.toJson("OK");
		} else {
			logger.info("Azienda | elimina | " + id + " | negato");
			
			return "Accesso negato";
		}
	}
	
	
	@PostMapping(value = "/modifica/nome")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String modificaNome(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		logger.info("Azienda | modifica | nome");
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Azienda | modifica | nome | concesso");
			
			CambioString cambio = gson.fromJson(param, CambioString.class);
			
			return gson.toJson(this.daoAzienda.cambiaNomeAzienda(cambio));
		} else {
			logger.info("Azienda | modifica | nome | concesso");
			
			return "Accesso negato";
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
