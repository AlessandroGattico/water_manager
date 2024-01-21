package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.user.*;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class ControllerUser {
	
	private final DAO daoUser;
	private final TokenService tokenService;
	
	
	@GetMapping(value = "/admin")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getAdmin(@RequestBody String username, String password) {
		Gson gson = new Gson();
		Admin admin = this.daoUser.getAdmin(username, password);
		
		return gson.toJson(admin);
	}
	
	/*
	@GetMapping(value = "/admin/disable/{id}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String disableUser(@PathVariable int id) {
		Gson gson = new Gson();
		Admin admin = this.daoUser.getAdmin();
		
		return gson.toJson(admin);
	}
	 */
	
	
	@GetMapping(value = "/get")
	@PreAuthorize("hasAuthority('SYSTTEMADMIN')")
	public String getUser(@RequestBody String username, String password) {
		Gson gson = new Gson();
		UserProfile userProfile = this.daoUser.getUser(username, password);
		
		return gson.toJson(userProfile);
	}
	
	
	@GetMapping(value = "/get/ga/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getGestoreAzienda(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			GestoreAzienda gestoreAzienda = this.daoUser.getGestoreAzienda(id);
			
			return gson.toJson(gestoreAzienda);
		} else {
			return gson.toJson(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accesso negato"));
			
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
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
		}
	}
	
	
	@GetMapping(value = "/getAll")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getUtenti() {
		Gson gson = new Gson();
		HashSet<UserProfile> users = this.daoUser.getUtenti();
		
		return gson.toJson(users);
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
	public ResponseEntity<Boolean> modificaNome(@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoUser.cambiaNomeUser(cambio));
	}
	
	
	@PostMapping(value = "/modifica/cognome")
	public ResponseEntity<Boolean> modificaCognome(@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoUser.cambiaCognomeUser(cambio));
	}
	
	
	@PostMapping(value = "/modifica/password")
	public ResponseEntity<Boolean> modificaPassword(@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoUser.cambiaPasswordUser(cambio));
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
}
