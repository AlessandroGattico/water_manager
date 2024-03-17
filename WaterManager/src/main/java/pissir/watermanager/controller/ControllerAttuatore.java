package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Attuatore;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.model.utils.cambio.CambioInt;
import pissir.watermanager.model.utils.cambio.CambioString;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/azienda/attuatore")
@RequiredArgsConstructor
public class ControllerAttuatore {
	
	private final DAO daoAttuatore;
	private final TokenService tokenService;
	
	
	@GetMapping(value = "/get/{id}")
	public String getAttuatoreId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			Attuatore attuatore = this.daoAttuatore.getAttuatoreId(id);
			
			return gson.toJson(attuatore);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/getCampo/{id}")
	public String getAttuatoriCampo(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		
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
		String jwt = extractTokenFromRequest(request);
		
		
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
		String jwt = extractTokenFromRequest(request);
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			this.daoAttuatore.deleteAttuatore(id);
			return gson.toJson("OK");
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public String modificaNome(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			CambioString cambio = gson.fromJson(param, CambioString.class);
			
			return gson.toJson(this.daoAttuatore.cambiaNomeAttuatore(cambio));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/modifica/campo")
	public String modificaCampo(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			CambioInt cambio = gson.fromJson(param, CambioInt.class);
			
			return gson.toJson(this.daoAttuatore.cambiaCampoAttuatore(cambio));
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
