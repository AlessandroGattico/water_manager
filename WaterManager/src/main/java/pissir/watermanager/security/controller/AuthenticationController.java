package pissir.watermanager.security.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.security.model.LoginRequestDTO;
import pissir.watermanager.security.model.RegistrationDTO;
import pissir.watermanager.security.services.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	private final Gson gson;
	private final DAO dao;
	
	
	@PostMapping("/register")
	public String registerUser(@RequestBody String body) {
		RegistrationDTO registration = this.gson.fromJson(body, RegistrationDTO.class);
		
		return this.gson.toJson(authenticationService.registerUser(registration));
	}
	
	
	@PostMapping("/login")
	public String loginUser(@RequestBody String body) {
		LoginRequestDTO request = this.gson.fromJson(body, LoginRequestDTO.class);
		
		return this.gson.toJson(authenticationService.loginUser(request));
	}
	
	
	@GetMapping("/verify/username/{username}")
	public ResponseEntity<Boolean> verificaUsername(@PathVariable String username) {
		boolean esiste = this.dao.existsByUsername(username);
		
		return ResponseEntity.ok(! esiste);
	}
	
	
	@GetMapping("/verify/mail/{email}")
	public ResponseEntity<Boolean> verificaEmail(@PathVariable String email) {
		boolean esiste = this.dao.existsByEmail(email);
		
		return ResponseEntity.ok(! esiste);
	}
	
	
}
