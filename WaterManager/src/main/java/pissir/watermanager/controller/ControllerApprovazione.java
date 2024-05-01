package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Approvazione;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;
import pissir.watermanager.security.utils.TokenCheck;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@RestController
@RequestMapping("/api/v1/bacino/approvazione")
@RequiredArgsConstructor
public class ControllerApprovazione {
	
	private static final Logger logger = LogManager.getLogger(ControllerApprovazione.class.getName());
	private final DAO daoApprovazione;
	private final TokenService tokenService;
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO') or hasAuthority('GESTOREAZIENDA')")
	public String getApprovazioneId(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Approvazione | get | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt,
				UserRole.GESTOREIDRICO) || this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREAZIENDA)) {
			logger.info("Approvazione | get | {} | concesso", id);
			
			Approvazione approvazione = this.daoApprovazione.getApprovazioneId(id);
			
			return gson.toJson(approvazione);
			
		} else {
			logger.info("Approvazione | get | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/add")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String addApprovazione(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Approvazione | add");
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			logger.info("Approvazione | add | concesso");
			
			Approvazione approvazione = gson.fromJson(param, Approvazione.class);
			
			this.daoApprovazione.addApprovazione(approvazione);
			
			return gson.toJson("OK");
		} else {
			logger.info("Approvazione | add | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAuthority('GESTOREIDRICO')")
	public String deleteApprovazione(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Approvazione | elimina | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.GESTOREIDRICO)) {
			logger.info("Approvazione | elimina | {} | concesso", id);
			
			this.daoApprovazione.deleteApprovazione(id);
			
			return gson.toJson("OK");
		} else {
			logger.info("Approvazione | elimina | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
}
