package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.RichiestaIdrica;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@RestController
@RequestMapping("/api/v1/richiesta")
@RequiredArgsConstructor
public class ControllerRichiestaIdrica {
	
	private final DAO daoRichieste;
	private final TokenService tokenService;
	private static final Logger logger = LogManager.getLogger(ControllerRichiestaIdrica.class.getName());
	
	
	@PostMapping(value = "/add")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String addRichiesta(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			RichiestaIdrica richiesta = gson.fromJson(param, RichiestaIdrica.class);
			
			return gson.toJson(this.daoRichieste.addRichiesta(richiesta));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('GESTOREIDRICO')")
	public String getRichiestaId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			RichiestaIdrica richiestaIdrica = this.daoRichieste.getRichiestaId(id);
			
			return gson.toJson(richiestaIdrica);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/allBacino/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String getRichiesteBacino(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteBacino(id);
			
			return gson.toJson(richieste);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/allColtivazione/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getRichiesteColtivazione(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteColtivazione(id);
			
			return gson.toJson(richieste);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping("/delete/{id}")
	public String deleteRichiesta(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.daoRichieste.deleteRichiesta(id);
			return gson.toJson("OK");
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
