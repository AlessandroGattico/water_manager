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
import pissir.watermanager.security.services.TokenService;
import pissir.watermanager.security.utils.TokenCheck;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class ControllerUser {
	
	private final DAO daoUser;
	private final TokenService tokenService;
	private static final Logger logger = LogManager.getLogger(ControllerUser.class.getName());
	
	
	@GetMapping(value = "/get/ga/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getGestoreAzienda(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
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
		String jwtToken = TokenCheck.extractTokenFromRequest(request);
		
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
	
	
}
