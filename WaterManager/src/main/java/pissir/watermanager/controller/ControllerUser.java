package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.user.GestoreAzienda;
import pissir.watermanager.model.user.GestoreIdrico;
import pissir.watermanager.model.user.UserProfile;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.model.utils.cambio.CambioString;
import pissir.watermanager.security.services.TokenService;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class ControllerUser {
	
	private final DAO daoUser;
	private final TokenService tokenService;
	public static final Logger logger = LogManager.getLogger(ControllerAdmin.class.getName());
	
	
	@GetMapping(value = "/get/ga/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getGestoreAzienda(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			GestoreAzienda gestoreAzienda = this.daoUser.getGestoreAzienda(id);
			
			return gson.toJson(gestoreAzienda);
		} else {
			return gson.toJson("Accesso negato");
			
		}
	}
	
	
	@GetMapping("/get/gi/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String getGestoreIdrico(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwtToken = extractTokenFromRequest(request);
		
		if (tokenService.validateTokenAndRole(jwtToken, UserRole.GESTOREIDRICO)) {
			GestoreIdrico gestoreIdrico = this.daoUser.getGestoreIdrico(id);
			
			return gson.toJson(gestoreIdrico);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<Integer> addUser(@RequestBody String param) {
		Gson gson = new Gson();
		UserProfile user = gson.fromJson(param, UserProfile.class);
		
		return ResponseEntity.ok(this.daoUser.addUser(user));
	}
	
	
	@DeleteMapping(value = "/delete")
	public void deleteUser(@RequestBody String param) {
		Gson gson = new Gson();
		UserProfile user = gson.fromJson(param, UserProfile.class);
		
		this.daoUser.deleteUser(user);
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public String modificaNome(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwtToken = extractTokenFromRequest(request);
		
		if (tokenService.validateTokenAndRole(jwtToken,
				UserRole.GESTOREIDRICO) || this.tokenService.validateTokenAndRole(jwtToken, UserRole.GESTOREAZIENDA)) {
			CambioString cambio = gson.fromJson(param, CambioString.class);
			
			return gson.toJson(this.daoUser.cambiaStringUser(cambio));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/modifica/cognome")
	public String modificaCognome(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwtToken = extractTokenFromRequest(request);
		
		if (tokenService.validateTokenAndRole(jwtToken,
				UserRole.GESTOREIDRICO) || this.tokenService.validateTokenAndRole(jwtToken, UserRole.GESTOREAZIENDA)) {
			CambioString cambio = gson.fromJson(param, CambioString.class);
			
			return gson.toJson(this.daoUser.cambiaStringUser(cambio));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/modifica/password")
	public String modificaPassword(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwtToken = extractTokenFromRequest(request);
		
		if (tokenService.validateTokenAndRole(jwtToken,
				UserRole.GESTOREIDRICO) || this.tokenService.validateTokenAndRole(jwtToken, UserRole.GESTOREAZIENDA)) {
			CambioString cambio = gson.fromJson(param, CambioString.class);
			
			return gson.toJson(this.daoUser.cambiaStringUser(cambio));
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
