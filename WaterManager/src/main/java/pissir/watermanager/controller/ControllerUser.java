package pissir.watermanager.controller;

import com.google.gson.Gson;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class ControllerUser {
	
	private final DAO daoUser;
	
	
	@GetMapping(value = "/admin")
	public String getAdmin (@RequestBody String username, String password) {
		Gson gson = new Gson();
		Admin admin = this.daoUser.getAdmin(username, password);
		
		return gson.toJson(admin);
	}
	
	
	@GetMapping(value = "/get")
	public String getUser (@RequestBody String username, String password) {
		Gson gson = new Gson();
		UserProfile userProfile = this.daoUser.getUser(username, password);
		
		return gson.toJson(userProfile);
	}
	
	
	@GetMapping(value = "/get/ga")
	public String getGestoreAzienda (String username, String password) {
		Gson gson = new Gson();
		GestoreAzienda gestoreAzienda = this.daoUser.getGestoreAzienda(username, password);
		
		return gson.toJson(gestoreAzienda);
	}
	
	
	@GetMapping("/get/gi")
	public String getGestoreIdrico (String username, String password) {
		Gson gson = new Gson();
		GestoreIdrico gestoreIdrico = this.daoUser.getGestoreIdrico(username, password);
		
		return gson.toJson(gestoreIdrico);
	}
	
	
	@GetMapping(value = "/getAll")
	public String getUtenti () {
		Gson gson = new Gson();
		HashSet<UserProfile> users = this.daoUser.getUtenti();
		
		return gson.toJson(users);
	}
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<Integer> addUser (@RequestBody String param) {
		Gson gson = new Gson();
		UserProfile user = gson.fromJson(param, UserProfile.class);
		
		return ResponseEntity.ok(this.daoUser.addUser(user));
	}
	
	
	@DeleteMapping(value = "/delete")
	public void deleteUser (@RequestBody String param) {
		Gson gson = new Gson();
		UserProfile user = gson.fromJson(param, UserProfile.class);
		
		this.daoUser.deleteUser(user);
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public ResponseEntity<Boolean> modificaNome (@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoUser.cambiaNomeUser(cambio));
	}
	
	
	@PostMapping(value = "/modifica/cognome")
	public ResponseEntity<Boolean> modificaCognome (@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoUser.cambiaCognomeUser(cambio));
	}
	
	
	@PostMapping(value = "/modifica/password")
	public ResponseEntity<Boolean> modificaPassword (@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoUser.cambiaPasswordUser(cambio));
	}
	
}
