package pissir.watermanager.dao;

import org.springframework.stereotype.Repository;
import pissir.watermanager.model.cambio.*;
import pissir.watermanager.model.item.*;
import pissir.watermanager.model.user.*;

import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DAO {
	
	private final DaoAzienda daoAzienda;
	private final DaoApprovazione daoApprovazione;
	private final DaoAttuatore daoAttuatore;
	private final DaoCampagna daoCampagna;
	private final DaoCampo daoCampo;
	private final DaoColtivazione daoColtivazione;
	private final DaoBacinoIdrico daoBacinoIdrico;
	private final DaoRichieste daoRichieste;
	private final DaoSensore daoSensore;
	private final DaoMisura daoMisura;
	private final DaoUser daoUser;
	private final DaoAttivazioni daoAttivazioni;
	private final DaoRisorseAzienda daoRisorseAzienda;
	private final DaoRisorseBacino daoRisorseBacino;
	
	
	public DAO () {
		this.daoAzienda = new DaoAzienda();
		this.daoApprovazione = new DaoApprovazione();
		this.daoAttuatore = new DaoAttuatore();
		this.daoCampagna = new DaoCampagna();
		this.daoCampo = new DaoCampo();
		this.daoColtivazione = new DaoColtivazione();
		this.daoBacinoIdrico = new DaoBacinoIdrico();
		this.daoRichieste = new DaoRichieste();
		this.daoSensore = new DaoSensore();
		this.daoMisura = new DaoMisura();
		this.daoUser = new DaoUser();
		this.daoAttivazioni = new DaoAttivazioni();
		this.daoRisorseAzienda = new DaoRisorseAzienda();
		this.daoRisorseBacino = new DaoRisorseBacino();
	}
	
	
	//------ USER ------
	public UserProfile getUser (String username, String password) {
		return this.daoUser.getUser(username, password);
	}
	
	
	public HashSet<UserProfile> getUtenti () {
		return this.daoUser.getUtenti();
	}
	
	
	public GestoreIdrico getGestoreIdrico (String username, String password) {
		GestoreIdrico gestoreIdrico = this.daoUser.getGestoreIdrico(username, password);
		
		BacinoIdrico bacinoIdrico = this.getBacinoGestore(gestoreIdrico.getId());
		
		if (bacinoIdrico != null) {
			HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseBacino(bacinoIdrico.getId());
			
			if (! risorse.isEmpty()) {
				bacinoIdrico.setRisorse(risorse);
			}
			
			gestoreIdrico.setBacinoIdrico(bacinoIdrico);
		}
		
		return gestoreIdrico;
	}
	
	
	public GestoreAzienda getGestoreAzienda (String username, String password) {
		GestoreAzienda gestoreAzienda = this.daoUser.getGestoreAzienda(username, password);
		
		Azienda azienda = this.getAziendaGestore(gestoreAzienda.getId());
		
		if (azienda != null) {
			HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseAzienda(azienda.getId());
			HashSet<Campagna> campagne = this.getCampagnaAzienda(azienda.getId());
			
			if (! risorse.isEmpty()) {
				azienda.setRisorse(risorse);
			}
			
			if (! campagne.isEmpty()) {
				azienda.setCampagne(campagne);
			}
			
			gestoreAzienda.setAzienda(azienda);
		}
		
		return gestoreAzienda;
	}
	
	
	private Azienda getAziendaGestore (int idGestore) {
		return this.daoAzienda.getAziendaUser(idGestore);
	}
	
	
	private BacinoIdrico getBacinoGestore (int idGestore) {
		return this.daoBacinoIdrico.getBacinoUser(idGestore);
	}
	
	
	public int addUser (UserProfile user) {
		return this.daoUser.addUser(user);
	}
	
	
	public void deleteUser (UserProfile user) {
		this.daoUser.deleteUser(user);
	}
	
	
	public Admin getAdmin (String username, String password) {
		Admin admin = this.daoUser.getAdmin(username, password);
		
		if (! admin.getGestoriAziende().isEmpty()) {
			for (GestoreAzienda gestoreAzienda : admin.getGestoriAziende()) {
				Azienda azienda = this.getAziendaGestore(gestoreAzienda.getId());
				
				if (azienda != null) {
					gestoreAzienda.setAzienda(azienda);
				}
			}
		}
		
		if (! admin.getGestoriIdrici().isEmpty()) {
			for (GestoreIdrico gestoreIdrico : admin.getGestoriIdrici()) {
				BacinoIdrico bacinoIdrico = this.getBacinoGestore(gestoreIdrico.getId());
				
				if (bacinoIdrico != null) {
					HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseBacino(bacinoIdrico.getId());
					
					if (! risorse.isEmpty()) {
						bacinoIdrico.setRisorse(risorse);
					}
					
					gestoreIdrico.setBacinoIdrico(bacinoIdrico);
				}
			}
		}
		
		return admin;
	}
	
	
	public Boolean cambiaNomeUser (CambioString cambio) {
		return this.daoUser.cambiaNome(cambio);
	}
	
	
	public Boolean cambiaCognomeUser (CambioString cambio) {
		return this.daoUser.cambiaCognome(cambio);
	}
	
	
	public Boolean cambiaPasswordUser (CambioString cambio) {
		return this.daoUser.cambiaPassword(cambio);
	}
	
	
	//------ BACINOIDRICO ------
	public BacinoIdrico getBacinoId (int idBacino) {
		return this.daoBacinoIdrico.getBacinoId(idBacino);
	}
	
	
	public HashSet<BacinoIdrico> getBacini () {
		return this.daoBacinoIdrico.getBacini();
	}
	
	
	public Integer addBacino (BacinoIdrico bacinoIdrico) {
		return this.daoBacinoIdrico.addBacino(bacinoIdrico);
	}
	
	
	public void deleteBacino (BacinoIdrico bacino) {
		this.daoBacinoIdrico.deleteBacino(bacino);
	}
	
	
	public Boolean cambiaNomeBacino (CambioString cambio) {
		return this.daoBacinoIdrico.cambiaNome(cambio);
	}
	
	
	//------ AZIENDA ------
	public Azienda getAziendaId (int idAzienda) {
		Azienda azienda = this.daoAzienda.getAziendaId(idAzienda);
		HashSet<Campagna> campagne = this.getCampagnaAzienda(idAzienda);
		HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseAzienda(idAzienda);
		
		if (! campagne.isEmpty()) {
			azienda.setCampagne(campagne);
		}
		
		if (! risorse.isEmpty()) {
			azienda.setRisorse(risorse);
		}
		
		return azienda;
	}
	
	
	public HashSet<Azienda> getAziende () {
		HashSet<Azienda> aziende = this.daoAzienda.getAziende();
		
		if (! aziende.isEmpty()) {
			for (Azienda azienda : aziende) {
				HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseAzienda(azienda.getId());
				if (! risorse.isEmpty()) {
					azienda.setRisorse(risorse);
				}
				
				HashSet<Campagna> campagne = this.getCampagnaAzienda(azienda.getId());
				
				if (! campagne.isEmpty()) {
					azienda.setCampagne(campagne);
				}
			}
		}
		
		return aziende;
	}
	
	
	public int addAzienda (Azienda azienda) {
		return this.daoAzienda.addAzienda(azienda);
	}
	
	
	public void deleteAzienda (int id) {
		this.daoAzienda.deleteAzienda(id);
	}
	
	
	public Boolean cambiaNomeAzienda (CambioString cambio) {
		return this.daoAzienda.cambiaNome(cambio);
	}
	
	
	//------ CAMPAGNA ------
	public Campagna getCampagnaId (int idCampagna) {
		Campagna campagna = this.daoCampagna.getCampagnaId(idCampagna);
		HashSet<Campo> campi = this.getCampiCampagna(idCampagna);
		
		if (! campi.isEmpty()) {
			campagna.setCampi(campi);
		}
		
		return campagna;
	}
	
	
	public HashSet<Campagna> getCampagnaAzienda (int idAzienda) {
		HashSet<Campagna> campagne = this.daoCampagna.getCampagnaAzienda(idAzienda);
		
		if (! campagne.isEmpty()) {
			for (Campagna campagna : campagne) {
				HashSet<Campo> campi = this.getCampiCampagna(campagna.getId());
				
				if (! campi.isEmpty()) {
					campagna.setCampi(campi);
				}
			}
		}
		
		return campagne;
	}
	
	
	public Integer addCampagna (Campagna campagna) {
		return this.daoCampagna.addCampagna(campagna);
	}
	
	
	public void deleteCampagna (int uuidCampagna) {
		this.daoCampagna.deleteCampagna(uuidCampagna);
	}
	
	
	public Boolean cambiaNomeCampagna (CambioString cambio) {
		return this.daoCampagna.cambiaNome(cambio);
	}
	
	
	//------ CAMPO ------
	public Campo getCampoId (int uuidCampo) {
		Campo campo = this.daoCampo.getCampoId(uuidCampo);
		HashSet<Coltivazione> cotivazioni = this.getColtivazioniCampo(campo.getId());
		HashSet<Attuatore> attuatori = this.getAttuatoriCampo(campo.getId());
		HashSet<Sensore> sensori = this.getSensoriCampo(campo.getId());
		
		if (! cotivazioni.isEmpty()) {
			campo.setColtivazioni(cotivazioni);
		}
		if (! attuatori.isEmpty()) {
			campo.setAttuatori(attuatori);
		}
		
		if (! sensori.isEmpty()) {
			campo.setSensori(this.getSensoriCampo(campo.getId()));
		}
		
		return campo;
	}
	
	
	public HashSet<Campo> getCampiCampagna (int idCampagna) {
		HashSet<Campo> campi = this.daoCampo.getCampiCampagna(idCampagna);
		
		
		if (! campi.isEmpty()) {
			for (Campo campo : campi) {
				HashSet<Coltivazione> cotivazioni = this.getColtivazioniCampo(campo.getId());
				HashSet<Attuatore> attuatori = this.getAttuatoriCampo(campo.getId());
				HashSet<Sensore> sensori = this.getSensoriCampo(campo.getId());
				
				if (! cotivazioni.isEmpty()) {
					campo.setColtivazioni(cotivazioni);
				}
				if (! attuatori.isEmpty()) {
					campo.setAttuatori(attuatori);
				}
				
				if (! sensori.isEmpty()) {
					campo.setSensori(sensori);
				}
			}
		}
		
		return campi;
	}
	
	
	public Integer addCampo (Campo campo) {
		return this.daoCampo.addCampo(campo);
	}
	
	
	public void deleteCampo (int idCampo) {
		this.daoCampo.deleteCampo(idCampo);
	}
	
	
	public Boolean cambiaNomeCampo (CambioString cambio) {
		return this.daoCampo.cambiaNome(cambio);
	}
	
	
	public Boolean cambiaCampagnaCampo (CambioInt cambio) {
		return this.daoCampo.cambiaCampagna(cambio);
	}
	
	//------ COLTIVAZIONE ------
	
	
	public Coltivazione getColtivazioneId (int idColtivazione) {
		return this.daoColtivazione.getColtivazioneId(idColtivazione);
	}
	
	
	public HashSet<Coltivazione> getColtivazioniCampo (int idCampo) {
		return this.daoColtivazione.getColtivazioniCampo(idCampo);
	}
	
	
	public Integer addColtivazione (Coltivazione coltivazione) {
		return this.daoColtivazione.addColtivazione(coltivazione);
	}
	
	
	public void deleteColtivazione (int idColtivazione) {
		this.daoColtivazione.deleteColtivazione(idColtivazione);
	}
	
	
	//------ SENSORE ------
	public Sensore getSensoreId (int idSensore) {
		Sensore sensore = this.daoSensore.getSensoreId(idSensore);
		HashSet<Misura> misure = this.daoMisura.getMisureSensore(idSensore);
		
		if (! misure.isEmpty()) {
			sensore.setMisure(misure);
		}
		
		return sensore;
	}
	
	
	public HashSet<Sensore> getSensoriCampo (int idCampo) {
		HashSet<Sensore> sensori = this.daoSensore.getSensoriCampo(idCampo);
		
		for (Sensore sensore : sensori) {
			HashSet<Misura> misure = this.daoMisura.getMisureSensore(sensore.getId());
			
			if (! misure.isEmpty()) {
				sensore.setMisure(misure);
			}
		}
		
		return sensori;
	}
	
	
	public int addSensore (Sensore sensore) {
		return this.daoSensore.addSensore(sensore);
	}
	
	
	public void deleteSensore (int idSensore) {
		this.daoSensore.deleteSensore(idSensore);
	}
	
	
	public Boolean cambiaNomeSensore (CambioString cambio) {
		return this.daoSensore.cambiaNome(cambio);
	}
	
	
	public Boolean cambiaCampoSensore (CambioInt cambio) {
		return this.daoSensore.cambiaCampo(cambio);
	}
	
	
	//------ MISURA ------
	public Misura getMisuraId (int idMisura) {
		return this.daoMisura.getMisuraId(idMisura);
	}
	
	
	public HashSet<Misura> getMisureSensore (int idSensore) {
		return this.daoMisura.getMisureSensore(idSensore);
	}
	
	
	public void addMisura (Misura misura) {
		this.daoMisura.addMisura(misura);
	}
	
	
	public void deleteMisura (int idMisura) {
		this.daoMisura.deleteMisura(idMisura);
	}
	
	
	//------ ATTUATORE ------
	public Attuatore getAttuatoreId (int idAttuatore) {
		Attuatore attuatore = this.daoAttuatore.getAttuatoreId(idAttuatore);
		HashSet<Attivazione> attivazioni = this.daoAttivazioni.getAttivazioniAttuatore(idAttuatore);
		
		if (! attivazioni.isEmpty()) {
			attuatore.setAttivazioni(attivazioni);
		}
		
		return attuatore;
	}
	
	
	public HashSet<Attuatore> getAttuatoriCampo (int idCampo) {
		HashSet<Attuatore> attuatori = this.daoAttuatore.getAttuatoriCampo(idCampo);
		
		for (Attuatore attuatore : attuatori) {
			HashSet<Attivazione> attivazioni = this.daoAttivazioni.getAttivazioniAttuatore(attuatore.getId());
			
			if (! attivazioni.isEmpty()) {
				attuatore.setAttivazioni(attivazioni);
			}
		}
		
		return attuatori;
	}
	
	
	public Integer addAttuatore (Attuatore attuatore) {
		return this.daoAttuatore.addAttuatore(attuatore);
	}
	
	
	public void deleteAttuatore (int idAttuatore) {
		this.daoAttuatore.deleteAttuatore(idAttuatore);
	}
	
	
	public Boolean cambiaNomeAttuatore (CambioString cambio) {
		return this.daoAttuatore.cambiaNome(cambio);
	}
	
	
	public Boolean cambiaCampoAttuatore (CambioInt cambio) {
		return this.daoAttuatore.cambiaCampo(cambio);
	}
	
	
	//------ ATTIVAZIONE ------
	public Attivazione getAttivazioneId (int idAttivazione) {
		return this.daoAttivazioni.getAttivazioneId(idAttivazione);
	}
	
	
	public HashSet<Attivazione> getAttivazioniAttuatore (int idAttuatore) {
		return this.daoAttivazioni.getAttivazioniAttuatore(idAttuatore);
	}
	
	
	public Integer addAttivazione (Attivazione attivazione) {
		return this.daoAttivazioni.addAttivazione(attivazione);
	}
	
	
	public void deleteAttivazione (int idAttivazione) {
		this.daoAttivazioni.deleteAttivazione(idAttivazione);
	}
	
	
	public Boolean cambiaAttivazione (CambioBool cambio) {
		return this.daoAttivazioni.cambiaAttivazione(cambio);
	}
	
	
	//------ RICHIESTA ------
	public RichiestaIdrica getRichiestaId (int idRichiesta) {
		return this.daoRichieste.getRichiestaId(idRichiesta);
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteColtivazione (int idColtivazione) {
		return this.daoRichieste.getRichiesteColtivazione(idColtivazione);
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteBacino (int idBacino) {
		return this.daoRichieste.getRichiesteBacino(idBacino);
	}
	
	
	/*
	public HashSet<RichiestaIdrica> getRichiesteAzienda (int idAzienda) {
		return this.daoRichieste.getRichiesteAzienda(idAzienda);
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteCampo (int idCampo) {
		return this.daoRichieste.getRichiesteCampo(idCampo);
	}
	
	
	
	 */
	public int addRichiesta (RichiestaIdrica richiesta) {
		return this.daoRichieste.addRichiesta(richiesta);
	}
	
	
	public void deleteRichiesta (int idRichiesta) {
		this.daoRichieste.deleteRichiesta(idRichiesta);
	}
	
	
	//------ APPROVAZIONE ------
	public Approvazione getApprovazioneId (int idApprovazione) {
		return this.daoApprovazione.getApprovazioneId(idApprovazione);
	}
	
	
	public HashSet<Approvazione> getApprovazioniGestore (int idGestore) {
		return this.daoApprovazione.getApprovazioniGestore(idGestore);
	}
	
	
	public Integer addApprovazione (Approvazione approvazione) {
		return this.daoApprovazione.addApprovazione(approvazione);
	}
	
	
	public void deleteApprovazione (int idApprovazione) {
		this.daoApprovazione.deleteApprovazione(idApprovazione);
	}
	
	
	public Boolean cambiaApprovazione (CambioBool cambio) {
		return this.daoApprovazione.cambiaApprovazione(cambio);
	}
	
	
	//------ RISORSEAZIENDA ------
	public RisorsaIdrica getRisorsaAziendaId (int idRisorsa) {
		return this.daoRisorseAzienda.getRisorsaAziendaId(idRisorsa);
	}
	
	
	public HashSet<RisorsaIdrica> getStoricoRisorseAzienda (int idAzienda) {
		return this.daoRisorseAzienda.getStoricoRisorseAzienda(idAzienda);
	}
	
	
	public int addRisorsaAzienda (RisorsaIdrica risorsaIdrica) {
		return this.daoRisorseAzienda.addRisorsaAzienda(risorsaIdrica);
	}
	
	
	public void deleteRisorsaAzienda (int uuid) {
		this.daoRisorseAzienda.deleteRisorsaAzienda(uuid);
	}
	
	
	//------ RISORSEBACINO ------
	public RisorsaIdrica getRisorsaBacinoId (int idRisorsa) {
		return this.daoRisorseBacino.getRisorsaBacinoId(idRisorsa);
	}
	
	
	public HashSet<RisorsaIdrica> getStoricoRisorseBacino (int idBacino) {
		return this.daoRisorseBacino.getStoricoRisorseBacino(idBacino);
	}
	
	
	public int addRisorsaBacino (RisorsaIdrica risorsaIdrica) {
		return this.daoRisorseBacino.addRisorsaBacino(risorsaIdrica);
	}
	
	
	public void deleteRisorsaBacino (int id) {
		this.daoRisorseBacino.deleteRisorsaBacino(id);
	}
	
	
	//
	public HashSet<String> getRaccolti () {
		return this.daoColtivazione.getRaccolti();
	}
	
	
	public void addRaccolto (String raccolto) {
		this.daoColtivazione.addRaccolto(raccolto);
	}
	
	
	public void deleteRaccolto (String nome) {
		this.daoColtivazione.deleteRaccolto(nome);
	}
	
	
	public HashSet<String> getEsigenze () {
		return this.daoColtivazione.getEsigenze();
	}
	
	
	public void addEsigenza (String esigenza) {
		this.daoColtivazione.addEsigenza(esigenza);
	}
	
	
	public void deleteEsigenza (String nome) {
		this.daoColtivazione.deleteEsigenza(nome);
	}
	
	
	public HashSet<String> getIrrigazioni () {
		return this.daoColtivazione.getIrrigazioni();
	}
	
	
	public void addIrrigazione (String nome) {
		this.daoColtivazione.addIrrigazione(nome);
	}
	
	
	public void deleteIrrigazione (String nome) {
		this.daoColtivazione.deleteIrrigazione(nome);
	}
	
	
}
