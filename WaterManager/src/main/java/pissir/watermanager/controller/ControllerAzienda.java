package pissir.watermanager.controller;

import com.google.gson.Gson;
import org.springframework.security.access.prepost.PreAuthorize;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.Azienda;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/azienda")
@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
@RequiredArgsConstructor
public class ControllerAzienda {
	
	private final DAO daoAzienda;
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<Integer> addAzienda (@RequestBody String param) {
		Gson gson = new Gson();
		Azienda azienda = gson.fromJson(param, Azienda.class);
		
		return ResponseEntity.ok(this.daoAzienda.addAzienda(azienda));
	}
	
	
	@GetMapping(value = "/get/{id}")
	public String getAziendaId (@PathVariable int id) {
		Gson gson = new Gson();
		Azienda azienda = this.daoAzienda.getAziendaId(id);
		
		return gson.toJson(azienda);
	}
	
	
	@GetMapping(value = "/get/all")
	public String getAziende () {
		Gson gson = new Gson();
		HashSet<Azienda> aziende = this.daoAzienda.getAziende();
		
		return gson.toJson(aziende);
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteAzienda (@PathVariable int id) {
		this.daoAzienda.deleteAzienda(id);
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public ResponseEntity<Boolean> modificaNome (@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoAzienda.cambiaNomeAzienda(cambio));
	}
	
}
