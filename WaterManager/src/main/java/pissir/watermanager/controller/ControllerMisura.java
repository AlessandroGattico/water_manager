package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Misura;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/misura")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
public class ControllerMisura {
	
	private final DAO daoMisura;
	private final TokenService tokenService;
	
	
	@PostMapping(value = "/add")
	public void addMisura(@RequestBody String param) {
		Gson gson = new Gson();
		Misura misura = gson.fromJson(param, Misura.class);
		
		this.daoMisura.addMisura(misura);
	}
	
	
	@GetMapping(value = "/get/{id}")
	public String getMisuraId(@PathVariable int id) {
		Gson gson = new Gson();
		Misura misura = this.daoMisura.getMisuraId(id);
		
		return gson.toJson(misura);
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	public String getMisureSensore(@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<Misura> misure = this.daoMisura.getMisureSensore(id);
		
		return gson.toJson(misure);
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteMisura(@PathVariable int id) {
		this.daoMisura.deleteMisura(id);
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
