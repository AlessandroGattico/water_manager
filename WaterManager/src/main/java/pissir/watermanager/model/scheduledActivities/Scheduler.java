package pissir.watermanager.model.scheduledActivities;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */
@Component
public class Scheduler {
	
	private final RisorsaService risorsaService;
	private final DatabaseCleaner cleaner;
	private final ConsumoAttuatori consumoAttuatori;
	
	
	public Scheduler(RisorsaService risorsaIdricaService, DatabaseCleaner cleaner, ConsumoAttuatori consumoAttuatori) {
		this.risorsaService = risorsaIdricaService;
		this.cleaner = cleaner;
		this.consumoAttuatori = consumoAttuatori;
	}
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void aggiornaRisorseIdriche() {
		this.risorsaService.aggiornaRisorse();
	}
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void clearDB() {
		this.cleaner.cleanDB();
	}
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void aggiornaConsumo() {
		this.consumoAttuatori.aggiornaConsumo();
	}
	
	
}
