package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Attuatore;
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
@RequestMapping("/api/v1/azienda/attuatore")
@RequiredArgsConstructor
public class ControllerAttuatore {
	
	private final DAO daoAttuatore;
	private final TokenService tokenService;
	private static final Logger logger = LogManager.getLogger(ControllerAttuatore.class.getName());
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getAttuatoreId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Attuatore | get | concesso");
			
			Attuatore attuatore = this.daoAttuatore.getAttuatoreId(id);
			
			return gson.toJson(attuatore);
		} else {
			logger.info("Attuatore | get | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/getCampo/{id}")
	public String getAttuatoriCampo(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			HashSet<Attuatore> attuatori = this.daoAttuatore.getAttuatoriCampo(id);
			
			return gson.toJson(attuatori);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/add")
	public String addAttuatore(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			Attuatore attuatore = gson.fromJson(param, Attuatore.class);
			
			return gson.toJson(this.daoAttuatore.addAttuatore(attuatore));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public String deleteAttuatore(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			this.daoAttuatore.deleteAttuatore(id);
			return gson.toJson("OK");
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
}
