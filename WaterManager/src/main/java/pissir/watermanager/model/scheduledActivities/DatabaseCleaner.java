package pissir.watermanager.model.scheduledActivities;

import org.springframework.stereotype.Service;
import pissir.watermanager.dao.*;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */
@Service
public class DatabaseCleaner {
	
	private final DaoAttivazioni daoAttivazioni;
	private final DaoRichieste daoRichieste;
	private final DaoRisorseBacino daoRisorseBacino;
	private final DaoRisorseAzienda daoRisorseAzienda;
	private final DaoMisura daoMisura;
	private final DaoApprovazione daoApprovazione;
	private final DaoColtivazione daoColtivazione;
	
	
	public DatabaseCleaner(DaoAttivazioni daoAttivazioni, DaoRichieste daoRichieste, DaoRisorseBacino daoRisorseBacino,
						   DaoRisorseAzienda daoRisorseAzienda, DaoMisura daoMisura, DaoApprovazione daoApprovazione,
						   DaoColtivazione daoColtivazione) {
		this.daoAttivazioni = daoAttivazioni;
		this.daoRichieste = daoRichieste;
		this.daoRisorseBacino = daoRisorseBacino;
		this.daoRisorseAzienda = daoRisorseAzienda;
		this.daoMisura = daoMisura;
		this.daoApprovazione = daoApprovazione;
		this.daoColtivazione = daoColtivazione;
	}
	
	
	protected void cleanDB() {
		this.clearApprovazioni();
		this.clearRichieste();
		this.clearRisorse();
		this.clearMisure();
		this.clearAttivazioni();
		this.clearColtivazioni();
	}
	
	
	private void clearColtivazioni() {
		this.daoColtivazione.clearColtivazioni();
	}
	
	
	private void clearRisorse() {
		this.daoRisorseAzienda.clearRisorse();
		this.daoRisorseBacino.clearRisorse();
	}
	
	
	private void clearMisure() {
		this.daoMisura.clearMisure();
	}
	
	
	private void clearAttivazioni() {
		this.daoAttivazioni.clearAttivazioni();
	}
	
	
	private void clearApprovazioni() {
		this.daoApprovazione.clearApprovazioni();
	}
	
	
	private void clearRichieste() {
		this.daoRichieste.clearRichieste();
	}
	
}
