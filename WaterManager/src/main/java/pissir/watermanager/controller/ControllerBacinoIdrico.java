package pissir.watermanager.controller;

import com.google.gson.Gson;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.BacinoIdrico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/bacino")
@RequiredArgsConstructor
public class ControllerBacinoIdrico {
	
	private final DAO daoBacino;
	
	
	@GetMapping(value = "/get/{id}")
	public String getBacinoId (@PathVariable int id) {
		Gson gson = new Gson();
		BacinoIdrico bacinoIdrico = this.daoBacino.getBacinoId(id);
		
		return gson.toJson(bacinoIdrico);
	}
	
	
	@GetMapping(value = "/get/all")
	public String getBacini () {
		Gson gson = new Gson();
		HashSet<BacinoIdrico> bacini = this.daoBacino.getBacini();
		
		return gson.toJson(bacini);
	}
	
	
	@PostMapping(value = "/add/{id}")
	public ResponseEntity<Integer> addBacino (@RequestBody String param) {
		Gson gson = new Gson();
		BacinoIdrico bacinoIdrico = gson.fromJson(param, BacinoIdrico.class);
		
		return ResponseEntity.ok(this.daoBacino.addBacino(bacinoIdrico));
	}
	
	
	@DeleteMapping(value = "/delete/{id}")
	public void deleteBacino (@RequestBody String param) {
		Gson gson = new Gson();
		BacinoIdrico bacinoIdrico = gson.fromJson(param, BacinoIdrico.class);
		
		this.daoBacino.deleteBacino(bacinoIdrico);
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public ResponseEntity<Boolean> modificaNome (@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoBacino.cambiaNomeBacino(cambio));
	}
}
