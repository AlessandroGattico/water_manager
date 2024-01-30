package pissir.watermanager.model.risorseManager;

import org.springframework.stereotype.Service;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Azienda;
import pissir.watermanager.model.item.BacinoIdrico;
import pissir.watermanager.model.item.RisorsaIdrica;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

@Service
public class RisorsaService {
	
	private final DAO dao;
	
	
	public RisorsaService(DAO risorsaIdricaDao) {
		this.dao = risorsaIdricaDao;
	}
	
	
	public void aggiornaRisorse() {
		HashSet<BacinoIdrico> bacini = this.dao.getBacini();
		
		if (bacini != null) {
			for (BacinoIdrico bacino : bacini) {
				this.aggiungiRisorsaIdricaBacino(bacino.getId());
			}
		}
		
		HashSet<Azienda> aziende = this.dao.getAziende();
		
		if (aziende != null) {
			for (Azienda azienda : aziende) {
				this.aggiungiRisorsaIdricaAzienda(azienda.getId());
			}
		}
	}
	
	
	private void aggiungiRisorsaIdricaBacino(int idBacino) {
		RisorsaIdrica ultimaRisorsa = this.dao.ultimaRisorsaBacino(idBacino);
		double nuovaDisponibilita = calcolaNuovaDisponibilita(ultimaRisorsa);
		
		RisorsaIdrica nuovaRisorsa = new RisorsaIdrica();
		nuovaRisorsa.setDisponibilita(nuovaDisponibilita);
		nuovaRisorsa.setData(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		this.dao.addRisorsaBacino(nuovaRisorsa);
	}
	
	
	private void aggiungiRisorsaIdricaAzienda(int idAzienda) {
		RisorsaIdrica ultimaRisorsa = this.dao.ultimaRisorsaAzienda(idAzienda);
		double nuovaDisponibilita = calcolaNuovaDisponibilita(ultimaRisorsa);
		
		RisorsaIdrica nuovaRisorsa = new RisorsaIdrica();
		nuovaRisorsa.setDisponibilita(nuovaDisponibilita);
		nuovaRisorsa.setData(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		this.dao.addRisorsaAzienda(nuovaRisorsa);
	}
	
	
	private double calcolaNuovaDisponibilita(RisorsaIdrica ultimaRisorsa) {
		return ultimaRisorsa.getDisponibilita();
	}
	
}

