package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Approvazione;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.model.utils.cambio.CambioBool;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/bacino/approvazione")
@RequiredArgsConstructor
public class ControllerApprovazione {
	
	private final DAO daoApprovazione;
	private final TokenService tokenService;
	
	
	@GetMapping(value = "/get/{id}")
	public String getApprovazioneId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			Approvazione approvazione = this.daoApprovazione.getApprovazioneId(id);
			
			return gson.toJson(approvazione);
			
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/getGestore/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String getApprovazioniGestore(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			HashSet<Approvazione> approvazioni = this.daoApprovazione.getApprovazioniGestore(id);
			
			return gson.toJson(approvazioni);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/add")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String addApprovazione(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			Approvazione approvazione = gson.fromJson(param, Approvazione.class);
			
			this.daoApprovazione.addApprovazione(approvazione);
			
			return "OK";
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String deleteApprovazione(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			this.daoApprovazione.deleteApprovazione(id);
			
			return gson.toJson("OK");
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/modifica/app")
	public String modificaApprovato(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			CambioBool cambio = gson.fromJson(param, CambioBool.class);
			
			return gson.toJson(this.daoApprovazione.cambiaApprovazione(cambio));
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
