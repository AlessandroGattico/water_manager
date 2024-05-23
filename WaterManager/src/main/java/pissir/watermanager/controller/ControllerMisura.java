package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Misura;
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
@RequestMapping("/api/v1/misura")
@RequiredArgsConstructor
public class ControllerMisura {
	
	private final DAO daoMisura;
	private final TokenService tokenService;
	private static final Logger logger = LogManager.getLogger(ControllerMisura.class.getName());
	
	
	@PostMapping(value = "/add")
	public void addMisura(@RequestBody String param) {
		Gson gson = new Gson();
		Misura misura = gson.fromJson(param, Misura.class);
		
		logger.info("Misura | add | {}", param);
		
		this.daoMisura.addMisura(misura);
	}
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getMisuraId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Misura | get | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Misura | get | {} | concesso", id);
			
			Misura misura = this.daoMisura.getMisuraId(id);
			
			return gson.toJson(misura);
		} else {
			logger.info("Misura | get | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getMisureSensore(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Misura | get misure sensore | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Misura | get misure sensore | {} | concesso", id);
			
			HashSet<Misura> misure = this.daoMisura.getMisureSensore(id);
			
			return gson.toJson(misure);
		} else {
			logger.info("Misura | get misure sensore | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String deleteMisura(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Misura | elimina | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Misura | elimina | {} | concesso", id);
			
			this.daoMisura.deleteMisura(id);
			
			return "OK";
		} else {
			logger.info("Misura | elimina | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
		
	}
	
	
}