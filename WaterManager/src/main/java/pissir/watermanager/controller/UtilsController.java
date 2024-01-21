package pissir.watermanager.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.BacinoIdrico;

import java.util.LinkedList;


@RequestMapping("/api/v1/utils")
@RestController
public class UtilsController {
	
	private DAO dao;
	
	
	@GetMapping(value = "/bacino/get/all")
	public String getBacini() {
		Gson gson = new Gson();
		LinkedList<BacinoIdrico> bacini = this.dao.getBaciniSelect();
		
		if (bacini == null) {
			return gson.toJson(new LinkedList<BacinoIdrico>());
		}
		
		return gson.toJson(bacini);
	}
	
	
	@GetMapping(value = "/raccolto/get/all")
	public String getRaccolti() {
		Gson gson = new Gson();
		
		return gson.toJson(this.dao.getRaccolti());
	}
	
	
	@GetMapping(value = "/irrigazione/get/all")
	public String getIrrigazioni() {
		Gson gson = new Gson();
		
		return gson.toJson(this.dao.getIrrigazioni());
	}
	
	
	@GetMapping(value = "/esigenza/get/all")
	public String getEsigenze() {
		Gson gson = new Gson();
		
		return gson.toJson(this.dao.getEsigenze());
	}
	
}
