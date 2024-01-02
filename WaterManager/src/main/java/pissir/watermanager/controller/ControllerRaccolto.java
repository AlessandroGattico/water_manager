package pissir.watermanager.controller;

import com.google.gson.Gson;
import com.watermanager.dao.DAO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author alessandrogattico
 */

@RestController
@RequestMapping("/api/v1/raccolto")
@RequiredArgsConstructor
public class ControllerRaccolto {
	
	private final DAO dao;
	
	
	@GetMapping(value = "/raccolto/get/all")
	public String getRaccolti () {
		Gson gson = new Gson();
		
		return gson.toJson(this.dao.getRaccolti());
	}
	
	
	@PostMapping(value = "/raccolto/add/{nome}")
	public void addRaccolto (@PathVariable String nome) {
		this.dao.addRaccolto(nome);
	}
	
	
	@DeleteMapping(value = "/raccolto/delete/{nome}")
	public void deleteRaccolto (@PathVariable String nome) {
		this.dao.deleteRaccolto(nome);
	}
	
	
	@GetMapping(value = "/esigenza/get/all")
	public String getEsigenze () {
		Gson gson = new Gson();
		
		return gson.toJson(this.dao.getEsigenze());
	}
	
	
	@PostMapping(value = "/esigenza/add/{nome}")
	public void addEsigenza (@PathVariable String nome) {
		this.dao.addEsigenza(nome);
	}
	
	
	@DeleteMapping(value = "/esigenza/delete/{nome}")
	public void deleteEsigenza (@PathVariable String nome) {
		this.dao.deleteEsigenza(nome);
	}
	
	
	@GetMapping(value = "/irrigazione/get/all")
	public String getIrrigazioni () {
		Gson gson = new Gson();
		
		return gson.toJson(this.dao.getIrrigazioni());
	}
	
	
	@PostMapping(value = "/irrigazione/add/{nome}")
	public void addIrrigazione (@PathVariable String nome) {
		this.dao.addIrrigazione(nome);
	}
	
	
	@DeleteMapping(value = "/irrigazione/delete/{nome}")
	public void deleteIrrigazione (@PathVariable String nome) {
		this.dao.deleteIrrigazione(nome);
	}
	
}
