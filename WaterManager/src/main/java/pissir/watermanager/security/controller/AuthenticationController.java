package pissir.watermanager.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.security.model.ApplicationUser;
import pissir.watermanager.security.model.LoginResponseDTO;
import pissir.watermanager.security.model.RegistrationDTO;
import pissir.watermanager.security.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/register")
	public ApplicationUser registerUser(@RequestBody RegistrationDTO body){
		return authenticationService.registerUser(body.getUsername(), body.getPassword());
	}
	
	@PostMapping("/login")
	public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
		return authenticationService.loginUser(body.getUsername(), body.getPassword());
	}
}
