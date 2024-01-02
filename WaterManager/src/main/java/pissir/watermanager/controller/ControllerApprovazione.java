package pissir.watermanager.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.CambioBool;
import pissir.watermanager.model.item.Approvazione;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/approvazione")
@RequiredArgsConstructor
public class ControllerApprovazione {
	
	private final DAO daoApprovazione;
	
	
	@GetMapping(value = "/get/{id}")
	public String getApprovazioneId (@PathVariable int id) {
		Gson gson = new Gson();
		Approvazione approvazione = this.daoApprovazione.getApprovazioneId(id);
		
		return gson.toJson(approvazione);
	}
	
	
	@GetMapping(value = "/getGestore/{id}")
	public String getApprovazioniGestore (@PathVariable int id) {
		HashSet<Approvazione> approvazioni = this.daoApprovazione.getApprovazioniGestore(id);
		Gson gson = new Gson();
		
		return gson.toJson(approvazioni);
	}
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<Integer> addApprovazione (@RequestBody String param) {
		Gson gson = new Gson();
		Approvazione approvazione = gson.fromJson(param, Approvazione.class);
		
		return ResponseEntity.ok(this.daoApprovazione.addApprovazione(approvazione));
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteApprovazione (@PathVariable int id) {
		this.daoApprovazione.deleteApprovazione(id);
	}
	
	
	@PostMapping(value = "/modifica/app")
	public ResponseEntity<Boolean> modificaApprovato (@RequestBody String param) {
		Gson gson = new Gson();
		CambioBool cambio = gson.fromJson(param, CambioBool.class);
		
		return ResponseEntity.ok(this.daoApprovazione.cambiaApprovazione(cambio));
	}
	
}
