package pissir.watermanager.controller;


import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Attivazione;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.model.utils.cambio.CambioBool;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/azienda/attivazione")
@RequiredArgsConstructor
public class ControllerAttivazione {
	
	private final DAO daoAttivazioni;
	private final TokenService tokenService;
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getAttivazioneId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			Attivazione attivazione = this.daoAttivazioni.getAttivazioneId(id);
			
			return gson.toJson(attivazione);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/getAttuatore/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getAttivazioniAttuatore(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			HashSet<Attivazione> attivazioni = this.daoAttivazioni.getAttivazioniAttuatore(id);
			
			return gson.toJson(attivazioni);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/add")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String addAttivazione(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			Attivazione attivazione = gson.fromJson(param, Attivazione.class);
			
			return gson.toJson(this.daoAttivazioni.addAttivazione(attivazione));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String deleteAttivazione(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			this.daoAttivazioni.deleteAttivazione(id);
			
			return gson.toJson("OK");
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/modifica/att")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String modificaAttivazione(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			CambioBool cambio = gson.fromJson(param, CambioBool.class);
			
			return gson.toJson(this.daoAttivazioni.cambiaAttivazione(cambio));
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
