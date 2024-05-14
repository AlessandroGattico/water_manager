package pissir.watermanager.scheduledActivities;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import pissir.watermanager.dao.DAO;
import pissir.watermanager.model.item.Azienda;
import pissir.watermanager.model.item.BacinoIdrico;
import pissir.watermanager.model.item.RichiestaIdrica;
import pissir.watermanager.model.item.RisorsaIdrica;

import java.time.LocalDate;
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
	
	
	@PostConstruct
	public void initialize() {
		this.aggiornaRisorseGiornoPrecedente();
	}
	
	
	private void aggiornaRisorseGiornoPrecedente() {
		HashSet<Azienda> aziende = this.dao.getAziende();
		
		if (aziende != null) {
			for (Azienda azienda : aziende) {
				aggiornaRisorseAziendaPerGiornoPrecedente(azienda.getId());
			}
		}
	}
	
	
	private void aggiornaRisorseAziendaPerGiornoPrecedente(int idAzienda) {
		LocalDate ieri = LocalDate.now().minusDays(1);
		String ieriString = ieri.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LinkedList<RichiestaIdrica> waitingIeri = this.dao.getWaitingAziendaPerData(idAzienda, ieriString);
		RisorsaIdrica ultimaRisorsa = this.dao.ultimaRisorsaAzienda(idAzienda);
		
		if (! waitingIeri.isEmpty()) {
			Double newDisp = 0.0;
			
			for (RichiestaIdrica risorsa : waitingIeri) {
				newDisp += risorsa.getQuantita();
			}
			
			RisorsaIdrica nuovaRisorsa = new RisorsaIdrica();
			nuovaRisorsa.setDisponibilita(newDisp);
			nuovaRisorsa.setData(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			if (LocalDateTime.parse(ultimaRisorsa.getData(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
					.toLocalDate().equals(LocalDate.now())) {
				nuovaRisorsa.setConsumo(ultimaRisorsa.getConsumo());
			} else if (LocalDateTime.parse(ultimaRisorsa.getData(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
					.toLocalDate().isBefore(LocalDate.now())) {
				nuovaRisorsa.setConsumo(0.0);
			}
			
			this.dao.addRisorsaAzienda(nuovaRisorsa);
			this.dao.deleteWaitingPerData(idAzienda, ieriString);
		}
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

