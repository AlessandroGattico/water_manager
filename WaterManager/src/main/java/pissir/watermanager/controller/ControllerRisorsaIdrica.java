package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.RisorsaIdrica;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@RestController
@RequestMapping("/api/v1/risorsa")
@RequiredArgsConstructor
public class ControllerRisorsaIdrica {
	
	private final DAO dao;
	private final TokenService tokenService;
	public static final Logger logger = LogManager.getLogger(ControllerRisorsaIdrica.class.getName());
	
	
	@GetMapping(value = "/azienda/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getRisorsaAziendaId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			RisorsaIdrica risorsaIdrica = this.dao.getRisorsaAziendaId(id);
			
			return gson.toJson(risorsaIdrica);
			
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/azienda/getStorico/{id}")
	public String getStoricoRisorseAzienda(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			HashSet<RisorsaIdrica> risorse = this.dao.getStoricoRisorseAzienda(id);
			
			return gson.toJson(risorse);
			
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/azienda/add")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String addRisorsaAzienda(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			RisorsaIdrica risorsaIdrica = gson.fromJson(param, RisorsaIdrica.class);
			
			return gson.toJson(this.dao.addRisorsaAzienda(risorsaIdrica));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/azienda/delete/{id}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String deleteRisorsaAzienda(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.dao.deleteRisorsaAzienda(id);
			
			return gson.toJson("OK");
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/bacino/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String getRisorsaBacinoId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			RisorsaIdrica risorsaIdrica = this.dao.getRisorsaBacinoId(id);
			
			return gson.toJson(risorsaIdrica);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/bacino/getStorico/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String getStoricoRisorseBacino(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			HashSet<RisorsaIdrica> risorse = this.dao.getStoricoRisorseBacino(id);
			
			return gson.toJson(risorse);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/bacino/add")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String addRisorsaBacino(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			RisorsaIdrica risorsaIdrica = gson.fromJson(param, RisorsaIdrica.class);
			
			return gson.toJson(this.dao.addRisorsaBacino(risorsaIdrica));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/bacino/delete/{id}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String deleteRisorsaBacino(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.dao.deleteRisorsaBacino(id);
			
			return gson.toJson("OK");
		} else {
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
