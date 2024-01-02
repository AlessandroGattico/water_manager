package pissir.watermanager.controller;

import com.google.gson.Gson;
import com.watermanager.dao.DAO;
import com.watermanager.model.cambio.CambioBool;
import com.watermanager.model.item.Attivazione;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/attivazione")
@RequiredArgsConstructor
public class ControllerAttivazione {
	
	private final DAO daoAttivazioni;
	
	
	@GetMapping(value = "/get/{id}")
	public String getAttivazioneId (@PathVariable int id) {
		Gson gson = new Gson();
		Attivazione attivazione = this.daoAttivazioni.getAttivazioneId(id);
		
		return gson.toJson(attivazione);
	}
	
	
	@GetMapping(value = "/getAttuatore/{id}")
	public String getAttivazioniAttuatore (@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<Attivazione> attivazioni = this.daoAttivazioni.getAttivazioniAttuatore(id);
		
		return gson.toJson(attivazioni);
	}
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<Integer> addAttivazione (@RequestBody String param) {
		Gson gson = new Gson();
		Attivazione attivazione = gson.fromJson(param, Attivazione.class);
		
		return ResponseEntity.ok(this.daoAttivazioni.addAttivazione(attivazione));
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteAttivazione (@PathVariable int id) {
		this.daoAttivazioni.deleteAttivazione(id);
	}
	
	@PostMapping(value = "/modifica/att")
	public ResponseEntity<Boolean> modificaAttivazione (@RequestBody String param) {
		Gson gson = new Gson();
		CambioBool cambio = gson.fromJson(param, CambioBool.class);
		
		return ResponseEntity.ok(this.daoAttivazioni.cambiaAttivazione(cambio));
	}
}
