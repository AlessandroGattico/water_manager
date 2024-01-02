package pissir.watermanager.controller;

import com.google.gson.Gson;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.Campagna;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/campagna")
@RequiredArgsConstructor
public class ControllerCampagna {
	private final DAO daoCampagna;

	@PostMapping(value = "/add/{id}")
	public ResponseEntity<Integer> addCampagna(@RequestBody String psram) {
		Gson gson = new Gson();
		Campagna campagna = gson.fromJson(psram, Campagna.class);

		return ResponseEntity.ok(this.daoCampagna.addCampagna(campagna));
	}

	@GetMapping(value = "/get/{id}")
	public String getCampagnaId(@PathVariable int id) {
		Gson gson = new Gson();
		Campagna campagna = this.daoCampagna.getCampagnaId(id);

		return gson.toJson(campagna);
	}

	@GetMapping(value = "/get/all/{id}")
	public String getCampagneAzienda(@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<Campagna> campagne = this.daoCampagna.getCampagnaAzienda(id);

		return gson.toJson(campagne);
	}

	@DeleteMapping(value = "/delete/{id}")
	public void deleteCampagna(@PathVariable int id) {
		this.daoCampagna.deleteCampagna(id);
	}
	
	
	@PostMapping(value = "/modifica/nome")
	public ResponseEntity<Boolean> modificaNome (@RequestBody String param) {
		Gson gson = new Gson();
		CambioString cambio = gson.fromJson(param, CambioString.class);
		
		return ResponseEntity.ok(this.daoCampagna.cambiaNomeCampagna(cambio));
	}
}
