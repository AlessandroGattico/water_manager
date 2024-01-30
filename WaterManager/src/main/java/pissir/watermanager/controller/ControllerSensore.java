package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.*;
import pissir.watermanager.model.item.Sensore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/azienda/sensore")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
public class ControllerSensore {
	
	private final DAO daoSensore;
	private final TokenService tokenService;
	
	
	@GetMapping(value = "/get/{id}")
	public String getSensoreId (@PathVariable int id) {
		Gson gson = new Gson();
		Sensore sensore = this.daoSensore.getSensoreId(id);
		
		return gson.toJson(sensore);
	}
	
	
	@GetMapping(value = "/getCampo/{id}")
	public String getSensoriCampo (@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<Sensore> sensori = this.daoSensore.getSensoriCampo(id);
		
		return gson.toJson(sensori);
	}
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<Integer> addSensore (@RequestBody String param) {
		Gson gson = new Gson();
		Sensore sensore = gson.fromJson(param, Sensore.class);
		
		return ResponseEntity.ok(this.daoSensore.addSensore(sensore));
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteSensore (@PathVariable int id) {
		this.daoSensore.deleteSensore(id);
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public ResponseEntity<Boolean> modificaNome (@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoSensore.cambiaNomeSensore(cambio));
	}
	
	
	@PostMapping(value = "/modifica/campo")
	public ResponseEntity<Boolean> modificaCampo (@RequestBody String param) {
		Gson gson = new Gson();
		CambioInt cambio = gson.fromJson(param, CambioInt.class);
		
		return ResponseEntity.ok(this.daoSensore.cambiaCampoSensore(cambio));
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
