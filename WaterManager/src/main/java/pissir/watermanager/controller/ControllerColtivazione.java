package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Coltivazione;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/azienda/coltivazione")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
public class ControllerColtivazione {
	
	private final DAO daoColtivazione;
	private final TokenService tokenService;
	
	
	@PostMapping(value = "/add")
	public String addColtivazione(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			Coltivazione coltivazione = gson.fromJson(param, Coltivazione.class);
			
			return gson.toJson(this.daoColtivazione.addColtivazione(coltivazione));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/{id}")
	public String getColtivazioneId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			Coltivazione coltivazione = this.daoColtivazione.getColtivazioneId(id);
			
			return gson.toJson(coltivazione);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	public String getColtivazioniCampo(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			HashSet<Coltivazione> coltivazioni = this.daoColtivazione.getColtivazioniCampo(id);
			
			return gson.toJson(coltivazioni);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteColtivazione(@PathVariable int id) {
		this.daoColtivazione.deleteColtivazione(id);
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
}
