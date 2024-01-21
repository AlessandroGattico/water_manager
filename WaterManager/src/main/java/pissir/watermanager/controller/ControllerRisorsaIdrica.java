package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.RisorsaIdrica;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/risorsa")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SYSTEMADMIN')")
public class ControllerRisorsaIdrica {
	
	private final DAO dao;
	
	
	@GetMapping(value = "/azienda/get/{id}")
	public String getRisorsaAziendaId (@PathVariable int id) {
		Gson gson = new Gson();
		RisorsaIdrica risorsaIdrica = this.dao.getRisorsaAziendaId(id);
		
		return gson.toJson(risorsaIdrica);
	}
	
	
	@GetMapping(value = "/azienda/getStorico/{id}")
	public String getStoricoRisorseAzienda (@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<RisorsaIdrica> risorse = this.dao.getStoricoRisorseAzienda(id);
		
		return gson.toJson(risorse);
	}
	
	
	@PostMapping(value = "/azienda/add")
	public ResponseEntity<Integer> addRisorsaAzienda (@RequestBody String param) {
		Gson gson = new Gson();
		RisorsaIdrica risorsaIdrica = gson.fromJson(param, RisorsaIdrica.class);
		
		return ResponseEntity.ok(this.dao.addRisorsaAzienda(risorsaIdrica));
	}
	
	
	@DeleteMapping(value = "/azienda/delete/{id}")
	public void deleteRisorsaAzienda (@PathVariable int id) {
		this.dao.deleteRisorsaAzienda(id);
	}
	
	
	@GetMapping(value = "/bacino/get/{id}")
	public String getRisorsaBacinoId (@PathVariable int id) {
		Gson gson = new Gson();
		RisorsaIdrica risorsaIdrica = this.dao.getRisorsaBacinoId(id);
		
		return gson.toJson(risorsaIdrica);
	}
	
	
	@GetMapping(value = "/bacino/getStorico/{id}")
	public String getStoricoRisorseBacino (@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<RisorsaIdrica> risorse = this.dao.getStoricoRisorseBacino(id);
		
		return gson.toJson(risorse);
	}
	
	
	@PostMapping(value = "/bacino/add")
	public ResponseEntity<Integer> addRisorsaBacino (@RequestBody String param) {
		Gson gson = new Gson();
		RisorsaIdrica risorsaIdrica = gson.fromJson(param, RisorsaIdrica.class);
		
		return ResponseEntity.ok(this.dao.addRisorsaBacino(risorsaIdrica));
	}
	
	
	@DeleteMapping(value = "/bacino/delete/{id}")
	public void deleteRisorsaBacino (@PathVariable int id) {
		this.dao.deleteRisorsaBacino(id);
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
