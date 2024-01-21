package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.Campagna;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/azienda/campagna")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
public class ControllerCampagna {
	
	private final DAO daoCampagna;
	private final TokenService tokenService;
	
	
	@PostMapping(value = "/add")
	public String addCampagna(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			Campagna campagna = gson.fromJson(param, Campagna.class);
			
			return gson.toJson(this.daoCampagna.addCampagna(campagna));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/{id}")
	public String getCampagnaId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			Campagna campagna = this.daoCampagna.getCampagnaId(id);
			
			return gson.toJson(campagna);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	public String getCampagneAzienda(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			HashSet<Campagna> campagne = this.daoCampagna.getCampagnaAzienda(id);
			
			return gson.toJson(campagne);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteCampagna(@PathVariable int id) {
		this.daoCampagna.deleteCampagna(id);
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public ResponseEntity<Boolean> modificaNome(@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoCampagna.cambiaNomeCampagna(cambio));
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
}
