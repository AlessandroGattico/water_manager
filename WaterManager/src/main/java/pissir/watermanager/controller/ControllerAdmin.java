package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Azienda;
import pissir.watermanager.model.item.BacinoIdrico;
import pissir.watermanager.model.user.*;
import pissir.watermanager.security.services.TokenService;
import pissir.watermanager.security.utils.TokenCheck;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class ControllerAdmin {
	
	private static final Logger logger = LogManager.getLogger(ControllerAdmin.class.getName());
	private final DAO dao;
	private final TokenService tokenService;
	
	
	@GetMapping("/get/{id}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getAdmin(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | login");
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			Admin admin = this.dao.getAdmin(id);
			
			logger.info("Admin | login | concesso");
			
			return gson.toJson(admin);
		} else {
			logger.info("Admin | login | rifiutato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/admin/disable/{id}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String disableUser(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | disable user | {}", id);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.dao.disableUser(id);
			
			logger.info("Admin | disable user | {} | concesso", id);
			
			return gson.toJson("OK");
		} else {
			logger.info("Admin | disable user | {} | negato", id);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/count")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getCount(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | get | count");
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Admin | get | count | concesso");
			
			return gson.toJson(this.dao.getcount());
		} else {
			logger.info("Admin | get | count | rifiutato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/bacino/get/all")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getBacini(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | get | bacini");
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			HashSet<BacinoIdrico> bacini = this.dao.getBaciniSelect();
			
			logger.info("Admin | get | bacini | concesso");
			
			if (bacini == null) {
				return gson.toJson(new LinkedList<BacinoIdrico>());
			}
			
			return gson.toJson(bacini);
		} else {
			logger.info("Admin | get | bacini | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/users/get/all")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getUsers(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | get | users");
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			HashSet<UserProfile> users = this.dao.getUsers();
			
			logger.info("Admin | get | users | concesso");
			
			if (users == null) {
				return gson.toJson(new HashSet<UserProfile>());
			}
			
			return gson.toJson(users);
		} else {
			
			logger.info("Admin | get | users | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/gi/get/all")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getGestoriBacino(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | get | gestori idrici");
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			HashSet<GestoreIdrico> gestori = this.dao.getGestoriIdrici();
			
			logger.info("Admin | get | gestori idrici | concesso");
			
			if (gestori == null) {
				return gson.toJson(new HashSet<GestoreIdrico>());
			}
			
			return gson.toJson(gestori);
		} else {
			
			logger.info("Admin | get | gestori idrici | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/ga/get/all")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getgestoriAzienda(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | get | gestori azienda");
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			HashSet<GestoreAzienda> gestori = this.dao.getGestoriAzienda();
			
			logger.info("Admin | get | gestori azienda | concesso");
			
			if (gestori == null) {
				return gson.toJson(new HashSet<Azienda>());
			}
			
			return gson.toJson(gestori);
		} else {
			logger.info("Admin | get | gestori azienda | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/esigenza/delete/{param}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String deleteEsigenza(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | delete | esigenza | " + param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Admin | elimina | esigenza | " + param + " | concesso");
			
			this.dao.deleteEsigenza(param);
			return "OK";
		} else {
			logger.info("Admin | elimina | esigenza | " + param + " | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/esigenza/add/{param}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String addEsigenza(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | add | esigenza | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Admin | add | esigenza | {} | concesso", param);
			
			this.dao.addEsigenza(param);
			return "OK";
		} else {
			logger.info("Admin | add | esigenza | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/raccolto/delete/{param}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String deleteRaccolto(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | elimina | raccolto | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Admin | add | raccolto | {} | concesso", param);
			
			this.dao.deleteRaccolto(param);
			return "OK";
		} else {
			logger.info("Admin | add | raccolto | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/raccolto/add")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String addRaccolto(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | add | raccolto | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Admin | add | raccolto | {} | concesso", param);
			
			this.dao.addRaccolto(gson.fromJson(param, String.class));
			return "OK";
		} else {
			logger.info("Admin | add | raccolto | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/irrigazione/delete/{param}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String deleteIrrigazione(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | elimina | irrigazione | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Admin | elimina | irrigazione | {} | concesso", param);
			
			this.dao.deleteIrrigazione(param);
			return "OK";
		} else {
			logger.info("Admin | elimina | irrigazione | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@PostMapping(value = "/irrigazione/add/{param}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String addIrrigazione(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | add | irrigazione | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Admin | add | irrigazione | {} | concesso", param);
			
			this.dao.addIrrigazione(param);
			
			return "OK";
		} else {
			logger.info("Admin | add | irrigazione | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/sensor/delete/{param}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String deleteSensorType(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | elimina | Sensor_types | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Admin | elimina | Sensor_types | {} | concesso", param);
			
			this.dao.deleteSensorType(param);
			
			return "OK";
		} else {
			logger.info("Admin | elimina | Sensor_types | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/sensor/add/{param}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String addSensorType(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | add | Sensor_types | {}", param);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.dao.addSensorType(param);
			
			logger.info("Admin | add | Sensor_types | {} | concesso", param);
			
			return "OK";
		} else {
			logger.info("Admin | add | Sensor_types | {} | negato", param);
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/aziende/get/all")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getAllAziende(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | get | aziende");
		
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Admin | get | aziende | concesso");
			
			return gson.toJson(this.dao.getAllAziende());
		} else {
			logger.info("Admin | get | aziende | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/campagne/get/all")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getAllCampagne(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | get | campagne");
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Admin | get | campagne | concesso");
			
			return gson.toJson(this.dao.getAllCampagne());
		} else {
			logger.info("Admin | get | campagne | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/campi/get/all")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getAllCampi(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = TokenCheck.extractTokenFromRequest(request);
		
		logger.info("Admin | get | campi");
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			logger.info("Admin | get | campi | concesso");
			
			return gson.toJson(this.dao.getAllCampi());
		} else {
			logger.info("Admin | get | campi | negato");
			
			return gson.toJson("Accesso negato");
		}
	}
	
}
