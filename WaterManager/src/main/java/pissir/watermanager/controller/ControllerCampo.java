package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Campo;
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
@RequestMapping("/api/v1/azienda/campo")
@RequiredArgsConstructor
public class ControllerCampo {
	
	private final DAO daoCampo;
	private final TokenService tokenService;
	private static final Logger logger = LogManager.getLogger(ControllerCampo.class.getName());
	
	
	@PostMapping(value = "/add")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String addCampo(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Campo | add | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campo | add | {} | concesso", param);
			
			Campo campo = gson.fromJson(param, Campo.class);
			
			return gson.toJson(this.daoCampo.addCampo(campo));
		} else {
			logger.info("Campo | add | {} | negato", param);
			
			return gson.toJson(0);
		}
	}
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getCampoId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Campo | get | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campo | get | {} | concesso", id);
			
			Campo campo = this.daoCampo.getCampoId(id);
			
			return gson.toJson(campo);
		} else {
			logger.info("Campo | get | {} | negato", id);
			
			return gson.toJson(0);
		}
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getCampiCampagna(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Campo | get campi campagna | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campo | get campi campagna | {} | concesso", id);
			
			HashSet<Campo> campi = this.daoCampo.getCampiCampagna(id);
			
			return gson.toJson(campi);
		} else {
			logger.info("Campo | get campi campagna | {} | negato", id);
			
			return gson.toJson(0);
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String deleteCampo(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Campo | elimina | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Campo | elimina | {} | concesso", id);
			
			this.daoCampo.deleteCampo(id);
			
			return "OK";
		} else {
			logger.info("Campo | elimina | {} | negato", id);
			
			return gson.toJson(0);
		}
	}
	
}
