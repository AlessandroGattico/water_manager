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
import pissir.watermanager.security.utils.TokenCheck;

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
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Richiesta idrica | add | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Richiesta idrica | add | {} | concesso", param);
			
			RichiestaIdrica richiesta = gson.fromJson(param, RichiestaIdrica.class);
			
			return gson.toJson(this.daoRichieste.addRichiesta(richiesta));
		} else {
			logger.info("Richiesta idrica | add | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('GESTOREIDRICO')")
	public String getRichiestaId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Richiesta idrica | get | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO) ||
				this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Richiesta idrica | get | {} | concesso", id);
			
			RichiestaIdrica richiestaIdrica = this.daoRichieste.getRichiestaId(id);
			
			return gson.toJson(richiestaIdrica);
		} else {
			logger.info("Richiesta idrica | get | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/allBacino/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String getRichiesteBacino(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Richiesta idrica | get all bacino | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			logger.info("Richiesta idrica | get all bacino | {} | concesso", id);
			
			HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteBacino(id);
			
			return gson.toJson(richieste);
		} else {
			logger.info("Richiesta idrica | get all bacino | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/allColtivazione/{id}")
	@PreAuthorize("hasAuthority('GESTOREAZIENDA')")
	public String getRichiesteColtivazione(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Richiesta idrica | get all coltivazione | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Richiesta idrica | get all coltivazione | {} | concesso", id);
			
			HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteColtivazione(id);
			
			return gson.toJson(richieste);
		} else {
			logger.info("Richiesta idrica | get all coltivazione | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping("/delete/{id}")
	public String deleteRichiesta(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Richiesta idrica | elimina | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Richiesta idrica | elimina | {} | concesso", id);
			
			this.daoRichieste.deleteRichiesta(id);
			
			return gson.toJson("OK");
		} else {
			logger.info("Richiesta idrica | elimina | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
}
