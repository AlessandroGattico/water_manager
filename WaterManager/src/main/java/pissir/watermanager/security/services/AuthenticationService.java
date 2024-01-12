package pissir.watermanager.security.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pissir.watermanager.model.user.UserProfile;
import pissir.watermanager.security.model.LoginRequestDTO;
import pissir.watermanager.security.model.LoginResponseDTO;
import pissir.watermanager.security.model.RegistrationDTO;
import pissir.watermanager.security.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder encoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final TokenService tokenService;
	
	
	public AuthenticationService(UserRepository userRepository, PasswordEncoder encoder,
								 AuthenticationManager authenticationManager, TokenService tokenService) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}
	
	
	public LoginResponseDTO registerUser(RegistrationDTO reg) {
		String encodedPassword = encoder.encode(reg.getPassword());
		
		UserProfile user =
				new UserProfile(reg.getNome(), reg.getCognome(), reg.getUsername(), reg.getMail(), encodedPassword,
						reg.getRole());
		
		userRepository.saveUser(user);
		
		
		return this.loginUser(new LoginRequestDTO(user.getUsername(), user.getPassword()));
	}
	
	
	public LoginResponseDTO loginUser(LoginRequestDTO login) {
		UserProfile user = userRepository.findByUsername(login.getUsername());
		
		String password = user.getPassword();
		
		user.setPassword(this.encoder.encode(password));
		
		if (user != null && encoder.matches(login.getPassword(), user.getPassword())) {
			String token = tokenService.generateJwt(user);
			
			return new LoginResponseDTO(user, token);
		} else {
			return new LoginResponseDTO(null, "");
		}
	}
	
}
