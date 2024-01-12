package pissir.watermanager.security.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.security.model.LoginRequestDTO;
import pissir.watermanager.security.model.RegistrationDTO;
import pissir.watermanager.security.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	private final Gson gson;
	
	
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
	
}
