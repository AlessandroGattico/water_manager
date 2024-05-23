package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.BacinoIdrico;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;
import pissir.watermanager.security.utils.TokenCheck;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@RestController
@RequestMapping("/api/v1/bacino")
@RequiredArgsConstructor
public class ControllerBacinoIdrico {
	
	private final DAO daoBacino;
	private final TokenService tokenService;
	private static final Logger logger = LogManager.getLogger(ControllerBacinoIdrico.class.getName());
	
	
	@GetMapping(value = "/get/{id}")
	public String getBacinoId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Bacino idrico | get | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			logger.info("Bacino idrico | get | {} | concesso", id);
			
			BacinoIdrico bacinoIdrico = this.daoBacino.getBacinoId(id);
			
			return gson.toJson(bacinoIdrico);
		} else {
			logger.info("Bacino idrico | get | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/get/gestore/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String getBacinoGi(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Bacino idrico | get | bacino gestore | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			logger.info("Bacino idrico | get | bacino gestore | {} | concesso", id);
			
			BacinoIdrico bacinoIdrico = this.daoBacino.getBacinoGestore(id);
			
			if (bacinoIdrico != null) {
				return gson.toJson(bacinoIdrico);
			} else {
				return null;
			}
		} else {
			logger.info("Bacino idrico | get | bacino gestore | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/add")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String addBacino(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Bacino idrico | add | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			logger.info("Bacino idrico | add | {} | concesso", param);
			
			BacinoIdrico bacinoIdrico = gson.fromJson(param, BacinoIdrico.class);
			
			return gson.toJson(this.daoBacino.addBacino(bacinoIdrico));
		} else {
			logger.info("Bacino idrico | add | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String deleteBacino(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Bacino idrico | elimina | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			logger.info("Bacino idrico | elimina | {} | concesso", id);
			
			this.daoBacino.deleteBacino(id);
			
			return gson.toJson("OK");
		} else {
			logger.info("Bacino idrico | elimina | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
}
