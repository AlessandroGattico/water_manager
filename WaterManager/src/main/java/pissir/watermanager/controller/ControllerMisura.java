package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Misura;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/misura")
@RequiredArgsConstructor
public class ControllerMisura {
	
	private final DAO daoMisura;
	private final TokenService tokenService;
	
	
	@PostMapping(value = "/add")
	public void addMisura(@RequestBody String param) {
		Gson gson = new Gson();
		Misura misura = gson.fromJson(param, Misura.class);
		
		this.daoMisura.addMisura(misura);
	}
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getMisuraId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			Misura misura = this.daoMisura.getMisuraId(id);
			
			return gson.toJson(misura);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getMisureSensore(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			HashSet<Misura> misure = this.daoMisura.getMisureSensore(id);
			
			return gson.toJson(misure);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String deleteMisura(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			
			this.daoMisura.deleteMisura(id);
			return "OK";
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