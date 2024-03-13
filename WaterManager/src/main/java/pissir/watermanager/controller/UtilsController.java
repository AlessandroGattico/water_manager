package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.BacinoIdrico;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.model.utils.Topics;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;
import java.util.LinkedList;


@RequestMapping("/api/v1/utils")
@RestController
@RequiredArgsConstructor
public class UtilsController {
	
	private final DAO dao;
	private final TokenService tokenService;
	
	
	@GetMapping(value = "/topics/get/all")
	public String getTopics() {
		Gson gson = new Gson();
		
		Topics topics = this.dao.getTopics();
		
		if (topics == null) {
			return gson.toJson(new Topics());
		}
		
		return gson.toJson(topics);
	}
	
	
	@GetMapping(value = "/bacino/get/all")
	public String getBacini(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			HashSet<BacinoIdrico> bacini = this.dao.getBaciniSelect();
			
			if (bacini == null) {
				return gson.toJson(new LinkedList<BacinoIdrico>());
			}
			
			return gson.toJson(bacini);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
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
	
	
	@GetMapping(value = "/check/username")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('GESTOREIDRICO')")
	public String checkUsername(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt,
				UserRole.GESTOREAZIENDA) || this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			String username = gson.fromJson(param, String.class);
			
			return gson.toJson(this.dao.existsByUsername(username));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/check/email")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('GESTOREIDRICO')")
	public String checkMail(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt,
				UserRole.GESTOREAZIENDA) || this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			String mail = gson.fromJson(param, String.class);
			
			return gson.toJson(this.dao.existsByEmail(mail));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/check/campagnaNome/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('GESTOREIDRICO')")
	public String checkCampagnaNome(@RequestBody String param, @PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt,
				UserRole.GESTOREAZIENDA) || this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			String campagna = gson.fromJson(param, String.class);
			
			return gson.toJson(this.dao.existsCampagnaAzienda(id, campagna));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/check/campoNome/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('GESTOREIDRICO')")
	public String checkCampoNome(@RequestBody String param, @PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt,
				UserRole.GESTOREAZIENDA) || this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			String campo = gson.fromJson(param, String.class);
			
			return gson.toJson(this.dao.existsCampoCampagna(id, campo));
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
