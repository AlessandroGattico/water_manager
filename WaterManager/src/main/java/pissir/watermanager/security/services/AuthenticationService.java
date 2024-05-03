package pissir.watermanager.security.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pissir.watermanager.model.user.UserProfile;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.model.LoginRequestDTO;
import pissir.watermanager.security.model.LoginResponseDTO;
import pissir.watermanager.security.model.RegistrationDTO;
import pissir.watermanager.security.repository.UserRepository;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Service
@Transactional
public class AuthenticationService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	private final RestTemplate restTemplate;
	
	
	public AuthenticationService(UserRepository userRepository, PasswordEncoder encoder,
								 AuthenticationManager authenticationManager, TokenService tokenService,
								 RestTemplate restTemplate) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
		this.restTemplate = restTemplate;
	}

	
	public LoginResponseDTO registerUser(RegistrationDTO reg) {
		UserRole role = null;
		
		switch (reg.getRole()) {
			case 0:
				role = UserRole.SYSTEMADMIN;
				break;
			case 1:
				role = UserRole.GESTOREAZIENDA;
				break;
			case 2:
				role = UserRole.GESTOREIDRICO;
			default:
				break;
		}
		
		UserProfile user =
				new UserProfile(reg.getNome(), reg.getCognome(), reg.getUsername(), reg.getMail(),
						encoder.encode(reg.getPassword()), role);
		
		this.userRepository.saveUser(user);
		
		
		return this.loginUser(new LoginRequestDTO(user.getUsername(), reg.getPassword()));
	}
	
	
	public LoginResponseDTO loginUser(LoginRequestDTO login) {
		UserProfile user = userRepository.findByUsername(login.getUsername());
		
		
		if (user != null && encoder.matches(login.getPassword(), user.getPassword())) {
			String token = tokenService.generateJwt(user);
			
			user.setPassword("");
			
			return new LoginResponseDTO(user, token);
		} else {
			return new LoginResponseDTO(null, "");
		}
	}
	
}
