package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.RichiestaIdrica;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/richiesta")
@RequiredArgsConstructor
public class ControllerRichiestaIdrica {
	
	private final DAO daoRichieste;
	private final TokenService tokenService;
	
	
	@PostMapping(value = "/add")
	public String addRichiesta(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			RichiestaIdrica richiesta = gson.fromJson(param, RichiestaIdrica.class);
			
			return gson.toJson(this.daoRichieste.addRichiesta(richiesta));
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/{id}")
	public String getRichiestaId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			RichiestaIdrica richiestaIdrica = this.daoRichieste.getRichiestaId(id);
			
			return gson.toJson(richiestaIdrica);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/allBacino/{id}")
	public String getRichiesteBacino(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteBacino(id);
			
			return gson.toJson(richieste);
		} else {
			return gson.toJson("Accesso negato");
		}
	}

	/*
	@GetMapping(value = "/get/allCampo/{id}")
	public String getRichiesteCampo(@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteCampo(id);

		return gson.toJson(richieste);
	}
	
	 */
	
	
	@GetMapping(value = "/get/allColtivazione/{id}")
	public String getRichiesteColtivazione(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteColtivazione(id);
			
			return gson.toJson(richieste);
		} else {
			return gson.toJson("Accesso negato");
		}
	}

	/*
	@GetMapping("/get/allAzienda/{id}")
	public String getRichiesteAzienda(@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteAzienda(id);

		return gson.toJson(richieste);
	}*/
	
	
	@DeleteMapping("/delete/{id}")
	public String deleteRichiesta(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.daoRichieste.deleteRichiesta(id);
			return "OK";
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
