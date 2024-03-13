package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.utils.cambio.CambioBool;
import pissir.watermanager.model.item.Approvazione;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/bacino/approvazione")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTOREIDRICO') or hasAuthority('SYSTEMADMIN')")
public class ControllerApprovazione {
	
	private final DAO daoApprovazione;
	private final TokenService tokenService;
	
	
	@GetMapping(value = "/get/{id}")
	public String getApprovazioneId(@PathVariable int id) {
		Gson gson = new Gson();
		Approvazione approvazione = this.daoApprovazione.getApprovazioneId(id);
		
		return gson.toJson(approvazione);
	}
	
	
	@GetMapping(value = "/getGestore/{id}")
	public String getApprovazioniGestore(@PathVariable int id) {
		HashSet<Approvazione> approvazioni = this.daoApprovazione.getApprovazioniGestore(id);
		Gson gson = new Gson();
		
		return gson.toJson(approvazioni);
	}
	
	
	@PostMapping(value = "/add")
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
	public void deleteApprovazione(@PathVariable int id) {
		this.daoApprovazione.deleteApprovazione(id);
	}
	
	
	@PostMapping(value = "/modifica/app")
	public ResponseEntity<Boolean> modificaApprovato(@RequestBody String param) {
		Gson gson = new Gson();
		CambioBool cambio = gson.fromJson(param, CambioBool.class);
		
		return ResponseEntity.ok(this.daoApprovazione.cambiaApprovazione(cambio));
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
}
