package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Azienda;
import pissir.watermanager.model.item.BacinoIdrico;
import pissir.watermanager.model.user.Admin;
import pissir.watermanager.model.user.GestoreAzienda;
import pissir.watermanager.model.user.GestoreIdrico;
import pissir.watermanager.model.user.UserRole;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;
import java.util.LinkedList;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAuthority('SYSTEMADMIN')")
public class ControllerAdmin {
	
	private final DAO dao;
	private final TokenService tokenService;
	
	
	@GetMapping("/get/{id}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getAdmin(@PathVariable int id, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			Admin admin = this.dao.getAdmin(id);
			
			return gson.toJson(admin);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/count")
	public String getCount(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			return gson.toJson(this.dao.getcount());
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/bacino/get/all")
	public String getBacini(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			HashSet<BacinoIdrico> bacini = this.dao.getBaciniSelect();
			
			if (bacini == null) {
				return gson.toJson(new LinkedList<BacinoIdrico>());
			}
			
			return gson.toJson(bacini);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/azienda/get/all")
	public String getAziende(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			HashSet<Azienda> aziende = this.dao.getAziende();
			
			if (aziende == null) {
				return gson.toJson(new HashSet<Azienda>());
			}
			
			return gson.toJson(aziende);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/gi/get/all")
	public String getGestoriBacino(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			HashSet<GestoreIdrico> gestori = this.dao.getGestoriIdrici();
			
			if (gestori == null) {
				return gson.toJson(new HashSet<GestoreIdrico>());
			}
			
			return gson.toJson(gestori);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/ga/get/all")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String getgestoriAzienda(HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			HashSet<GestoreAzienda> gestori = this.dao.getGestoriAzienda();
			
			if (gestori == null) {
				return gson.toJson(new HashSet<Azienda>());
			}
			
			return gson.toJson(gestori);
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/esigenza/delete/{param}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String deleteEsigenza(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.dao.deleteEsigenza(param);
			return "OK";
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/esigenza/add/{param}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String addEsigenza(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.dao.addEsigenza(param);
			return "OK";
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/raccolto/delete/{param}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String deleteRaccolto(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.dao.deleteRaccolto(param);
			return "OK";
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@GetMapping(value = "/raccolto/add")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String addRaccolto(@RequestBody String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.dao.addRaccolto(gson.fromJson(param, String.class));
			return "OK";
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/irrigazione/delete/{param}")
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	public String deleteIrrigazione(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.dao.deleteIrrigazione(param);
			return "OK";
		} else {
			return gson.toJson("Accesso negato");
		}
	}
	
	
	@DeleteMapping(value = "/irrigazione/add/{param}")
	public String addIrrigazione(@PathVariable String param, HttpServletRequest request) {
		Gson gson = new Gson();
		String jwt = extractTokenFromRequest(request);
		
		if (this.tokenService.validateTokenAndRole(jwt, UserRole.SYSTEMADMIN)) {
			this.dao.addIrrigazione(param);
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
