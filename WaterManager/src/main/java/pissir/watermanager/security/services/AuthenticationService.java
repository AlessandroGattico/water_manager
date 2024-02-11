package pissir.watermanager.security.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pissir.watermanager.model.user.UserProfile;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.model.GithubUser;
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
	private final RestTemplate restTemplate;
	private final String githubUserApiUrl = "https://api.github.com/user";
	
	
	public AuthenticationService(UserRepository userRepository, PasswordEncoder encoder,
								 AuthenticationManager authenticationManager, TokenService tokenService,
								 RestTemplate restTemplate) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
		this.restTemplate = restTemplate;
	}
	
	public LoginResponseDTO authenticateWithGithub(String accessToken) {
		// Richiedi i dettagli dell'utente da GitHub
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		ResponseEntity<GithubUser> response = restTemplate.exchange(
				githubUserApiUrl, HttpMethod.GET, entity, GithubUser.class);
		GithubUser githubUser = response.getBody();
		
		if (githubUser == null) {
			throw new RuntimeException("Failed to retrieve user details from GitHub.");
		}
		
		return authenticateGithubUser(githubUser);
	}
	
	public LoginResponseDTO authenticateGithubUser(GithubUser githubUser) {
		// Verifica se l'utente esiste nel database
		UserProfile existingUser = userRepository.findByUsername(githubUser.getLogin());
		
		if (existingUser == null) {
			// Crea un nuovo utente
			UserProfile newUser = new UserProfile(githubUser.getName(), "", githubUser.getLogin(),
					githubUser.getEmail(), "", null);
			userRepository.saveUser(newUser);
			existingUser = newUser;
		}
		
		// Genera JWT per l'utente
		String token = tokenService.generateJwt(existingUser);
		
		return new LoginResponseDTO(existingUser, token);
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
