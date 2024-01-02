package pissir.watermanager.controller;

import com.google.gson.Gson;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.CambioInt;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.Campo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/campo")
@RequiredArgsConstructor
public class ControllerCampo {
	
	private final DAO daoCampo;
	
	
	@PostMapping(value = "/add/{id}")
	public ResponseEntity<Integer> addCampo (@RequestBody String param) {
		Gson gson = new Gson();
		Campo campo = gson.fromJson(param, Campo.class);
		
		return ResponseEntity.ok(this.daoCampo.addCampo(campo));
	}
	
	
	@GetMapping(value = "/get/{id}")
	public String getCampoId (@PathVariable int id) {
		Gson gson = new Gson();
		Campo campo = this.daoCampo.getCampoId(id);
		
		return gson.toJson(campo);
	}
	
	
	@GetMapping(value = "/get/all/{id}")
	public String getCampiCampagna (@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<Campo> campi = this.daoCampo.getCampiCampagna(id);
		
		return gson.toJson(campi);
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteCampo (@PathVariable int id) {
		this.daoCampo.deleteCampo(id);
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public ResponseEntity<Boolean> modificaNome (@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoCampo.cambiaNomeCampo(cambio));
	}
	
	
	@PostMapping(value = "/modifica/campagna")
	public ResponseEntity<Boolean> modificaCampagna (@RequestBody String param) {
		Gson gson = new Gson();
		CambioInt cambio = gson.fromJson(param, CambioInt.class);
		
		return ResponseEntity.ok(this.daoCampo.cambiaCampagnaCampo(cambio));
	}
	
}
