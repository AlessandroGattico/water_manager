package pissir.watermanager.security.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.security.model.*;
import pissir.watermanager.security.services.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	private final Gson gson;
	private final DAO dao;
	
	@Value("${github.clientId}")
	private String githubClientId;
	
	@Value("${github.clientSecret}")
	private String githubClientSecret;
	
	
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
	
	
	@GetMapping("/oauth2/authorization/github")
	public String redirectToGithubOAuth() {
		String registrationId = "github";
		String redirectUri =
				OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + registrationId;
		// Redirigere l'utente al provider OAuth
		return "redirect:" + redirectUri;
	}
	
	
	@GetMapping("/login/oauth2/code/github")
	public String handleGithubOAuth2Callback(@RequestParam("code") String code) {
		// Scambia il codice di autorizzazione con un token di accesso
		String accessToken = getAccessTokenFromGithub(code);
		
		// Ottieni i dettagli dell'utente da GitHub usando il token di accesso
		GithubUser githubUser = fetchUserDetailsFromGithub(accessToken);
		
		// Autentica l'utente nel tuo sistema e genera un JWT
		LoginResponseDTO response = authenticationService.authenticateGithubUser(githubUser);
		
		// Restituisci la risposta appropriata con il JWT
		return gson.toJson(response);
	}
	
	
	private String getAccessTokenFromGithub(String code) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", githubClientId); // Sostituisci con il tuo client ID
		params.add("client_secret", githubClientSecret); // Sostituisci con il tuo client secret
		params.add("code", code);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		
		String accessTokenUrl = "https://github.com/login/oauth/access_token";
		ResponseEntity<AccessTokenResponse> response =
				restTemplate.postForEntity(accessTokenUrl, request, AccessTokenResponse.class);
		
		if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
			return response.getBody().getAccessToken();
		} else {
			throw new RuntimeException("Failed to retrieve access token from GitHub.");
		}
	}
	
	
	private GithubUser fetchUserDetailsFromGithub(String accessToken) {
		// Utilizza RestTemplate per ottenere i dettagli dell'utente da GitHub
		RestTemplate restTemplate = new RestTemplate();
		String userInfoUrl = "https://api.github.com/user";
		
		// Aggiungi l'header di autorizzazione con il token di accesso
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "token " + accessToken);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		ResponseEntity<GithubUser> response =
				restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, GithubUser.class);
		
		// Restituisci i dettagli dell'utente
		return response.getBody(); // Assicurati che GithubUser abbia i campi necessari per l'utente GitHub
	}
	
}
