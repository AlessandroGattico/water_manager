package pissir.watermanager.security.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.security.model.LoginRequestDTO;
import pissir.watermanager.security.model.RegistrationDTO;
import pissir.watermanager.security.services.AuthenticationService;


/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	private final Gson gson;
	private final DAO dao;
	private static final Logger logger = LogManager.getLogger(AuthenticationController.class.getName());
	
	
	@PostMapping("/register")
	public String registerUser(@RequestBody String body) {
		RegistrationDTO registration = this.gson.fromJson(body, RegistrationDTO.class);
		
		logger.info("User | register | " + registration.getUsername());
		
		return this.gson.toJson(authenticationService.registerUser(registration));
	}
	
	
	@PostMapping("/login")
	public String loginUser(@RequestBody String body) {
		LoginRequestDTO request = this.gson.fromJson(body, LoginRequestDTO.class);
		
		logger.info("User | login | " + request.getUsername());
		
		return this.gson.toJson(authenticationService.loginUser(request));
	}
	
	
	@GetMapping("/verify/username/{username}")
	public ResponseEntity<Boolean> verificaUsername(@PathVariable String username) {
		boolean esiste = this.dao.existsByUsername(username);
		
		logger.info("User | verifica username | " + username);
		
		return ResponseEntity.ok(! esiste);
	}
	
	
	@GetMapping("/verify/mail/{email}")
	public ResponseEntity<Boolean> verificaEmail(@PathVariable String email) {
		boolean esiste = this.dao.existsByEmail(email);
		
		logger.info("User | verifica mail | " + email);
		
		return ResponseEntity.ok(! esiste);
	}
	
}
