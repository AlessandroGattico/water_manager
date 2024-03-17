package pissir.watermanager.model.risorseManager;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Alessandro Gattico
 */

@Component
public class RisorsaComponent {
	
	private final RisorsaService risorsaService;
	
	
	public RisorsaComponent(RisorsaService risorsaIdricaService) {
		this.risorsaService = risorsaIdricaService;
	}
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void aggiornaRisorseIdriche() {
		this.risorsaService.aggiornaRisorse();
	}
	
}
