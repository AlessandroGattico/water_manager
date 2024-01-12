package pissir.watermanager.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Coltivazione;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/azienda/campagna/campo/coltivazione")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTOREAZIENDA') or hasAuthority('SYSTEMADMIN')")
public class ControllerColtivazione {
	
	private final DAO daoColtivazione;
	
	
	@PostMapping(value = "/add/{id}")
	public ResponseEntity<Integer> addColtivazione(@RequestBody String param) {
		Gson gson = new Gson();
		Coltivazione coltivazione = gson.fromJson(param, Coltivazione.class);
		
		return ResponseEntity.ok(this.daoColtivazione.addColtivazione(coltivazione));
	}
	
	
	@GetMapping(value = "/get/{id}")
	public String getColtivazioneId(@PathVariable int id) {
		Gson gson = new Gson();
		Coltivazione coltivazione = this.daoColtivazione.getColtivazioneId(id);
		
		return gson.toJson(coltivazione);
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	public String getColtivazioniCampo(@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<Coltivazione> coltivazioni = this.daoColtivazione.getColtivazioniCampo(id);
		
		return gson.toJson(coltivazioni);
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteColtivazione(@PathVariable int id) {
		this.daoColtivazione.deleteColtivazione(id);
	}
	
}
