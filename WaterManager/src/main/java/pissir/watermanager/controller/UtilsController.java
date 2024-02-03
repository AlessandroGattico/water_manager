package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;


@RequestMapping("/api/v1/utils")
@RestController
@RequiredArgsConstructor
public class UtilsController {
	
	private final DAO dao;
	private final TokenService tokenService;
	
	
	@GetMapping(value = "/raccolto/get/all")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
	public String getRaccolti(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt,
				UserRole.GESTOREAZIENDA) || this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			
			return gson.toJson(this.dao.getRaccolti());
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/irrigazione/get/all")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
	public String getIrrigazioni(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt,
				UserRole.GESTOREAZIENDA) || this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			
			return gson.toJson(this.dao.getIrrigazioni());
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/esigenza/get/all")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
	public String getEsigenze(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt,
				UserRole.GESTOREAZIENDA) || this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			
			return gson.toJson(this.dao.getEsigenze());
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/sensorTypes/get/all")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
	public String getSensorTypes(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt,
				UserRole.GESTOREAZIENDA) || this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			
			return gson.toJson(this.dao.getSensorTypes());
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
}