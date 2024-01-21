package pissir.watermanager.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.CambioInt;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.Attuatore;
import pissir.watermanager.security.services.TokenService;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/azienda/attuatore")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
public class ControllerAttuatore {
	
	private final DAO daoAttuatore;
	private final TokenService tokenService;
	
	
	@GetMapping(value = "/get/{id}")
	public String getAttuatoreId(@PathVariable int id) {
		Gson gson = new Gson();
		Attuatore attuatore = this.daoAttuatore.getAttuatoreId(id);
		
		return gson.toJson(attuatore);
	}
	
	
	@GetMapping(value = "/getCampo/{id}")
	public String getAttuatoriCampo(@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<Attuatore> attuatori = this.daoAttuatore.getAttuatoriCampo(id);
		
		return gson.toJson(attuatori);
	}
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<Integer> addAttuatore(@RequestBody String param) {
		Gson gson = new Gson();
		Attuatore attuatore = gson.fromJson(param, Attuatore.class);
		
		return ResponseEntity.ok(this.daoAttuatore.addAttuatore(attuatore));
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteAttuatore(@PathVariable int id) {
		this.daoAttuatore.deleteAttuatore(id);
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public ResponseEntity<Boolean> modificaNome(@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoAttuatore.cambiaNomeAttuatore(cambio));
	}
	
	
	@PostMapping(value = "/modifica/campo")
	public ResponseEntity<Boolean> modificaCampo(@RequestBody String param) {
		Gson gson = new Gson();
		CambioInt cambio = gson.fromJson(param, CambioInt.class);
		
		return ResponseEntity.ok(this.daoAttuatore.cambiaCampoAttuatore(cambio));
	}
	
	
	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
}
