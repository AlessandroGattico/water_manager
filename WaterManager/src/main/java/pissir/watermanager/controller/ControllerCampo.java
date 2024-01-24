package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.CambioInt;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.Campo;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/azienda/campo")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
public class ControllerCampo {
	
	private final DAO daoCampo;
	private final TokenService tokenService;
	
	
	@PostMapping(value = "/add")
	public String addCampo(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			Campo campo = gson.fromJson(param, Campo.class);
			
			return gson.toJson(this.daoCampo.addCampo(campo));
		} else {
			return gson.toJson(0);
		}
	}
	
	
	@GetMapping(value = "/get/{id}")
	public String getCampoId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			Campo campo = this.daoCampo.getCampoId(id);
			
			return gson.toJson(campo);
		} else {
			return gson.toJson(0);
		}
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	public String getCampiCampagna(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			HashSet<Campo> campi = this.daoCampo.getCampiCampagna(id);
			
			return gson.toJson(campi);
		} else {
			return gson.toJson(0);
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteCampo(@PathVariable int id) {
		this.daoCampo.deleteCampo(id);
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public ResponseEntity<Boolean> modificaNome(@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoCampo.cambiaNomeCampo(cambio));
	}
	
	
	@PostMapping(value = "/modifica/campagna")
	public ResponseEntity<Boolean> modificaCampagna(@RequestBody String param) {
		Gson gson = new Gson();
		CambioInt cambio = gson.fromJson(param, CambioInt.class);
		
		return ResponseEntity.ok(this.daoCampo.cambiaCampagnaCampo(cambio));
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
}
