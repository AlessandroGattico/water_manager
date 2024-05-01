package pissir.watermanager.model.scheduledActivities;

import org.springframework.stereotype.Service;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Azienda;
import pissir.watermanager.model.item.BacinoIdrico;
import pissir.watermanager.model.item.RichiestaIdrica;
import pissir.watermanager.model.item.RisorsaIdrica;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

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
		
		RisorsaIdrica nuovaRisorsa = new RisorsaIdrica();
		nuovaRisorsa.setDisponibilita(ultimaRisorsa.getDisponibilita());
		nuovaRisorsa.setData(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		nuovaRisorsa.setConsumo(0.0);
		
		this.dao.addRisorsaBacino(nuovaRisorsa);
	}
	
	
	private void aggiungiRisorsaIdricaAzienda(int idAzienda) {
		RisorsaIdrica ultimaRisorsa = this.dao.ultimaRisorsaAzienda(idAzienda);
		LinkedList<RichiestaIdrica> waiting = this.dao.getWaitingAzienda(idAzienda);
		
		if (! waiting.isEmpty()) {
			Double newDisp = 0.0;
			
			for (RichiestaIdrica risorsa : waiting) {
				newDisp += risorsa.getQuantita();
			}
			
			RisorsaIdrica nuovaRisorsa = new RisorsaIdrica();
			nuovaRisorsa.setDisponibilita(newDisp);
			nuovaRisorsa.setData(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			nuovaRisorsa.setConsumo(0.0);
			
			this.dao.addRisorsaAzienda(nuovaRisorsa);
			this.dao.deleteWaiting(idAzienda);
		}
		
	}
	
}

