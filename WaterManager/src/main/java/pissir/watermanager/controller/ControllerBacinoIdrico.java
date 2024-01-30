package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.Azienda;
import pissir.watermanager.model.item.BacinoIdrico;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/bacino")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTOREIDRICO') or hasAuthority('SYSTEMADMIN')")
public class ControllerBacinoIdrico {
	
	private final DAO daoBacino;
	private final TokenService tokenService;
	
	
	@GetMapping(value = "/get/{id}")
	public String getBacinoId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			BacinoIdrico bacinoIdrico = this.daoBacino.getBacinoId(id);
			
			return gson.toJson(bacinoIdrico);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/all")
	public String getBacini() {
		Gson gson = new Gson();
		HashSet<BacinoIdrico> bacini = this.daoBacino.getBacini();
		
		return gson.toJson(bacini);
	}
	
	@GetMapping(value = "/get/gestore/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String getBacinoGi(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			BacinoIdrico bacinoIdrico = this.daoBacino.getBacinoGestore(id);
			
			if (bacinoIdrico != null) {
				return gson.toJson(bacinoIdrico);
			} else {
				return null;
			}
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/add")
	public String addBacino(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			BacinoIdrico bacinoIdrico = gson.fromJson(param, BacinoIdrico.class);
			
			return gson.toJson(this.daoBacino.addBacino(bacinoIdrico));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public String deleteBacino(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			BacinoIdrico bacinoIdrico = gson.fromJson(param, BacinoIdrico.class);
			
			this.daoBacino.deleteBacino(bacinoIdrico);
			return "OK";
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public String modificaNome(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			CambioString cambio = gson.fromJson(param, CambioString.class);
			
			return gson.toJson(this.daoBacino.cambiaNomeBacino(cambio));
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
