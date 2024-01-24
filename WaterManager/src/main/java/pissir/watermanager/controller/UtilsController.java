package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.BacinoIdrico;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;

import java.util.LinkedList;


@RequestMapping("/api/v1/utils")
@RestController
@RequiredArgsConstructor
public class UtilsController {
	
	private final DAO dao;
	private final TokenService tokenService;
	
	
	
	@GetMapping(value = "/bacino/get/all")
	public String getBacini(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			LinkedList<BacinoIdrico> bacini = this.dao.getBaciniSelect();
			
			if (bacini == null) {
				return gson.toJson(new LinkedList<BacinoIdrico>());
			}
			
			return gson.toJson(bacini);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/raccolto/get/all")
	public String getRaccolti() {
		Gson gson = new Gson();
		
		return gson.toJson(this.dao.getRaccolti());
	}
	
	
	@GetMapping(value = "/irrigazione/get/all")
	public String getIrrigazioni() {
		Gson gson = new Gson();
		
		return gson.toJson(this.dao.getIrrigazioni());
	}
	
	
	@GetMapping(value = "/esigenza/get/all")
	public String getEsigenze() {
		Gson gson = new Gson();
		
		return gson.toJson(this.dao.getEsigenze());
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
}
