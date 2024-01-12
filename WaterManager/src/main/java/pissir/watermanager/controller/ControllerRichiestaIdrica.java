package pissir.watermanager.controller;

import com.google.gson.Gson;
import org.springframework.security.access.prepost.PreAuthorize;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.RichiestaIdrica;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/richiesta")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('GESTOREIDRICO') or hasAuthority('SYSTEMADMIN') or hasAuthority('GESTOREAZIENDA')")
public class ControllerRichiestaIdrica {
	
	private final DAO daoRichieste;
	
	
	@PostMapping(value = "/add")
	public ResponseEntity<Integer> addRichiesta (@RequestBody String param) {
		Gson gson = new Gson();
		RichiestaIdrica richiesta = gson.fromJson(param, RichiestaIdrica.class);
		
		return ResponseEntity.ok(this.daoRichieste.addRichiesta(richiesta));
	}
	
	
	@GetMapping(value = "/get/{id}")
	public String getRichiestaId (@PathVariable int id) {
		Gson gson = new Gson();
		RichiestaIdrica richiestaIdrica = this.daoRichieste.getRichiestaId(id);
		
		return gson.toJson(richiestaIdrica);
	}
	
	
	@GetMapping(value = "/get/allBacino/{id}")
	public String getRichiesteBacino (@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteBacino(id);
		
		return gson.toJson(richieste);
	}

	/*
	@GetMapping(value = "/get/allCampo/{id}")
	public String getRichiesteCampo(@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteCampo(id);

		return gson.toJson(richieste);
	}
	
	 */
	
	
	@GetMapping(value = "/get/allColtivazione/{id}")
	public String getRichiesteColtivazione (@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteColtivazione(id);
		
		return gson.toJson(richieste);
	}

	/*
	@GetMapping("/get/allAzienda/{id}")
	public String getRichiesteAzienda(@PathVariable int id) {
		Gson gson = new Gson();
		HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteAzienda(id);

		return gson.toJson(richieste);
	}*/
	
	
	@DeleteMapping("/delete/{id}")
	public void deleteRichiesta (@PathVariable int id) {
		this.daoRichieste.deleteRichiesta(id);
	}
	
}
