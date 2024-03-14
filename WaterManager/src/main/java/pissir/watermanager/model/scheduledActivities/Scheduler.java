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
	
	
	public Scheduler(RisorsaService risorsaIdricaService, DatabaseCleaner cleaner) {
		this.risorsaService = risorsaIdricaService;
		this.cleaner = cleaner;
	}
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void aggiornaRisorseIdriche() {
		this.risorsaService.aggiornaRisorse();
	}
	
}
