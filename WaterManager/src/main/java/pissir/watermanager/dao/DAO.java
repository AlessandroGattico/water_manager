package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.*;
import pissir.watermanager.model.user.*;
import pissir.watermanager.model.utils.ElementsCount;
import pissir.watermanager.model.utils.TopicCreator;
import pissir.watermanager.model.utils.Topics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
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
	private final DaoUtils daoUtils;
	public static final Logger logger = LogManager.getLogger(DAO.class.getName());
	
	
	public DAO() {
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
		this.daoUtils = new DaoUtils();
	}
	
	
	//------ USER ------
	public UserProfile getUserByUsername(String username) {
		return this.daoUser.getUserByUsername(username);
	}
	
	
	public GestoreIdrico getGestoreIdrico(int id) {
		GestoreIdrico gestoreIdrico = this.daoUser.getGestoreIdrico(id);
		
		BacinoIdrico bacinoIdrico = this.getBacinoGestore(gestoreIdrico.getId());
		
		if (bacinoIdrico != null) {
			HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseBacino(bacinoIdrico.getId());
			HashSet<RichiestaIdrica> richieste = this.getRichiesteBacino(bacinoIdrico.getId());
			
			if (richieste != null) {
				for (RichiestaIdrica richiesta : richieste) {
					Approvazione approvazione = this.daoApprovazione.getApprovazioneIdRichiesta(richiesta.getId());
					
					if (approvazione != null) {
						richiesta.setApprovato(approvazione);
					} else {
						richiesta.setApprovato(null);
					}
				}
				
				bacinoIdrico.setRichieste(richieste);
			}
			
			if (! risorse.isEmpty()) {
				bacinoIdrico.setRisorse(risorse);
			}
			
			gestoreIdrico.setBacinoIdrico(bacinoIdrico);
		}
		
		return gestoreIdrico;
	}
	
	
	public GestoreAzienda getGestoreAzienda(int id) {
		GestoreAzienda gestoreAzienda = this.daoUser.getGestoreAzienda(id);
		
		Azienda azienda = this.getAziendaGestore(gestoreAzienda.getId());
		
		if (azienda != null) {
			HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseAzienda(azienda.getId());
			HashSet<Campagna> campagne = this.getCampagnaAzienda(azienda.getId());
			
			if (risorse != null) {
				azienda.setRisorse(risorse);
			}
			
			if (risorse != null) {
				azienda.setCampagne(campagne);
			}
			
			gestoreAzienda.setAzienda(azienda);
		}
		
		return gestoreAzienda;
	}
	
	
	public Azienda getAziendaGestore(int idGestore) {
		Azienda azienda = this.daoAzienda.getAziendaUser(idGestore);
		
		if (azienda != null) {
			HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseAzienda(azienda.getId());
			HashSet<RichiestaIdrica> richieste = this.getRichiesteAzienda(azienda.getNome());
			
			if (risorse != null) {
				azienda.setRisorse(risorse);
			}
			
			if (richieste != null) {
				for (RichiestaIdrica richiesta : richieste) {
					Approvazione approvazione = this.daoApprovazione.getApprovazioneIdRichiesta(richiesta.getId());
					
					if (approvazione != null) {
						richiesta.setApprovato(approvazione);
					} else {
						richiesta.setApprovato(null);
					}
				}
				
				azienda.setRichieste(richieste);
			}
		}
		
		return azienda;
	}
	
	
	public BacinoIdrico getBacinoGestore(int idGestore) {
		BacinoIdrico bacino = this.daoBacinoIdrico.getBacinoUser(idGestore);
		
		if (bacino != null) {
			HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseBacino(bacino.getId());
			HashSet<RichiestaIdrica> richieste = this.getRichiesteBacino(bacino.getId());
			
			if (risorse != null) {
				bacino.setRisorse(risorse);
			}
			
			if (richieste != null) {
				bacino.setRichieste(richieste);
			}
		}
		
		return bacino;
	}
	
	
	public int addUser(UserProfile user) {
		return this.daoUser.addUser(user);
	}
	
	
	public void deleteUser(UserProfile user) {
		this.daoUser.deleteUser(user);
	}
	
	
	public Admin getAdmin(int id) {
		return this.daoUser.getAdmin(id);
	}
	
	
	//------ BACINOIDRICO ------
	public BacinoIdrico getBacinoId(int idBacino) {
		BacinoIdrico bacino = this.daoBacinoIdrico.getBacinoId(idBacino);
		
		if (bacino != null) {
			HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteBacino(idBacino);
			
			if (richieste != null) {
				bacino.setRichieste(richieste);
			}
			
			HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseBacino(bacino.getId());
			
			if (! risorse.isEmpty()) {
				bacino.setRisorse(risorse);
			}
		}
		
		return bacino;
	}
	
	
	public HashSet<BacinoIdrico> getBacini() {
		
		return this.daoBacinoIdrico.getBacini();
	}
	
	
	public HashSet<BacinoIdrico> getBaciniSelect() {
		return this.daoBacinoIdrico.getBacini();
	}
	
	
	public int addBacino(BacinoIdrico bacinoIdrico) {
		int result = this.daoBacinoIdrico.addBacino(bacinoIdrico);
		
		if (result != 0) {
			this.addRisorsaBacino(
					new RisorsaIdrica(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
							0.0,
							0.0, result));
			
			return result;
		}
		return 0;
	}
	
	
	public void deleteBacino(int bacino) {
		this.daoBacinoIdrico.deleteBacino(bacino);
	}
	
	
	//------ AZIENDA ------
	public Azienda getAziendaId(int idAzienda) {
		Azienda azienda = this.daoAzienda.getAziendaId(idAzienda);
		if (azienda != null) {
			HashSet<Campagna> campagne = this.getCampagnaAzienda(idAzienda);
			HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseAzienda(idAzienda);
			HashSet<RichiestaIdrica> richieste = this.getRichiesteAzienda(azienda.getNome());
			
			if (campagne != null) {
				azienda.setCampagne(campagne);
			}
			
			if (risorse != null) {
				azienda.setRisorse(risorse);
			}
			
			if (richieste != null) {
				azienda.setRichieste(richieste);
			}
		}
		return azienda;
	}
	
	
	public HashSet<Azienda> getAziende() {
		HashSet<Azienda> aziende = this.daoAzienda.getAziende();
		
		if (! aziende.isEmpty()) {
			for (Azienda azienda : aziende) {
				HashSet<RisorsaIdrica> risorse = this.getStoricoRisorseAzienda(azienda.getId());
				
				if (risorse != null) {
					azienda.setRisorse(risorse);
				}
				
				HashSet<Campagna> campagne = this.getCampagnaAzienda(azienda.getId());
				
				if (campagne != null) {
					azienda.setCampagne(campagne);
				}
				
				HashSet<RichiestaIdrica> richieste = this.getRichiesteAzienda(azienda.getNome());
				
				if (richieste != null) {
					azienda.setRichieste(richieste);
				}
			}
		}
		
		return aziende;
	}
	
	
	public Topics getTopics() {
		HashSet<Azienda> aziende = this.daoAzienda.getAziende();
		Topics topics = new Topics();
		
		if (! aziende.isEmpty()) {
			for (Azienda azienda : aziende) {
				
				HashSet<Campagna> campagne = this.getCampagnaAzienda(azienda.getId());
				
				for (Campagna campagna : campagne) {
					if (! campagna.getCampi().isEmpty()) {
						for (Campo campo : campagna.getCampi()) {
							if (! campo.getSensori().isEmpty()) {
								for (Sensore sensore : campo.getSensori()) {
									TopicCreator creator = new TopicCreator();
									
									creator.setIdAzienda(azienda.getId());
									creator.setIdCampagna(campagna.getId());
									creator.setIdCampo(campo.getId());
									creator.setNomeSensore(sensore.getNome());
									creator.setIdSensore(sensore.getId());
									creator.setTypeSensore(sensore.getType());
									creator.createTopic();
									
									topics.addTopic(creator);
								}
							}
						}
					}
				}
			}
		}
		
		return topics;
	}
	
	
	public int addAzienda(Azienda azienda) {
		int result = this.daoAzienda.addAzienda(azienda);
		
		if (result != 0) {
			this.addRisorsaAzienda(
					new RisorsaIdrica(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
							0.0, 0.0, result));
		}
		return 0;
	}
	
	
	public void deleteAzienda(int id) {
		this.daoAzienda.deleteAzienda(id);
	}
	
	
	//------ CAMPAGNA ------
	public Campagna getCampagnaId(int idCampagna) {
		Campagna campagna = this.daoCampagna.getCampagnaId(idCampagna);
		if (campagna != null) {
			HashSet<Campo> campi = this.getCampiCampagna(idCampagna);
			
			if (campi != null) {
				campagna.setCampi(campi);
			}
		}
		
		return campagna;
	}
	
	
	public HashSet<Campagna> getCampagnaAzienda(int idAzienda) {
		HashSet<Campagna> campagne = this.daoCampagna.getCampagnaAzienda(idAzienda);
		
		if (campagne != null) {
			for (Campagna campagna : campagne) {
				HashSet<Campo> campi = this.getCampiCampagna(campagna.getId());
				
				if (campi != null) {
					campagna.setCampi(campi);
				}
			}
		}
		
		return campagne;
	}
	
	
	public Integer addCampagna(Campagna campagna) {
		return this.daoCampagna.addCampagna(campagna);
	}
	
	
	public void deleteCampagna(int uuidCampagna) {
		this.daoCampagna.deleteCampagna(uuidCampagna);
	}
	
	
	//------ CAMPO ------
	public Campo getCampoId(int idCampo) {
		Campo campo = this.daoCampo.getCampoId(idCampo);
		
		if (campo != null) {
			HashSet<Coltivazione> coltivazioni = this.getColtivazioniCampo(campo.getId());
			HashSet<Attuatore> attuatori = this.getAttuatoriCampo(campo.getId());
			HashSet<Sensore> sensori = this.getSensoriCampo(campo.getId());
			
			if (coltivazioni != null) {
				campo.setColtivazioni(coltivazioni);
			}
			if (attuatori != null) {
				campo.setAttuatori(attuatori);
			}
			
			if (sensori != null) {
				campo.setSensori(this.getSensoriCampo(campo.getId()));
			}
		}
		
		return campo;
	}
	
	
	public HashSet<Campo> getCampiCampagna(int idCampagna) {
		HashSet<Campo> campi = this.daoCampo.getCampiCampagna(idCampagna);
		
		
		if (campi != null) {
			for (Campo campo : campi) {
				HashSet<Coltivazione> cotivazioni = this.getColtivazioniCampo(campo.getId());
				HashSet<Attuatore> attuatori = this.getAttuatoriCampo(campo.getId());
				HashSet<Sensore> sensori = this.getSensoriCampo(campo.getId());
				
				if (cotivazioni != null) {
					campo.setColtivazioni(cotivazioni);
				}
				if (attuatori != null) {
					campo.setAttuatori(attuatori);
				}
				
				if (sensori != null) {
					campo.setSensori(this.getSensoriCampo(campo.getId()));
				}
			}
		}
		
		return campi;
	}
	
	
	public Integer addCampo(Campo campo) {
		return this.daoCampo.addCampo(campo);
	}
	
	
	public void deleteCampo(int idCampo) {
		this.daoCampo.deleteCampo(idCampo);
	}
	
	
	//------ COLTIVAZIONE ------
	
	
	public Coltivazione getColtivazioneId(int idColtivazione) {
		return this.daoColtivazione.getColtivazioneId(idColtivazione);
	}
	
	
	public HashSet<Coltivazione> getColtivazioniCampo(int idCampo) {
		return this.daoColtivazione.getColtivazioniCampo(idCampo);
	}
	
	
	public int addColtivazione(Coltivazione coltivazione) {
		return this.daoColtivazione.addColtivazione(coltivazione);
	}
	
	
	public void deleteColtivazione(int idColtivazione) {
		this.daoColtivazione.deleteColtivazione(idColtivazione);
	}
	
	
	//------ SENSORE ------
	public Sensore getSensoreId(int idSensore) {
		Sensore sensore = this.daoSensore.getSensoreId(idSensore);
		HashSet<Misura> misure = this.daoMisura.getMisureSensore(idSensore);
		
		if (misure != null) {
			sensore.setMisure(misure);
		}
		
		return sensore;
	}
	
	
	public HashSet<Sensore> getSensoriCampo(int idCampo) {
		HashSet<Sensore> sensori = this.daoSensore.getSensoriCampo(idCampo);
		
		for (Sensore sensore : sensori) {
			HashSet<Misura> misure = this.daoMisura.getMisureSensore(sensore.getId());
			
			if (misure != null) {
				sensore.setMisure(misure);
			}
		}
		
		return sensori;
	}
	
	
	public int addSensore(Sensore sensore) {
		return this.daoSensore.addSensore(sensore);
	}
	
	
	public void deleteSensore(int idSensore) {
		this.daoSensore.deleteSensore(idSensore);
	}
	
	
	//------ MISURA ------
	public Misura getMisuraId(int idMisura) {
		return this.daoMisura.getMisuraId(idMisura);
	}
	
	
	public HashSet<Misura> getMisureSensore(int idSensore) {
		return this.daoMisura.getMisureSensore(idSensore);
	}
	
	
	public void addMisura(Misura misura) {
		this.daoMisura.addMisura(misura);
	}
	
	
	public void deleteMisura(int idMisura) {
		this.daoMisura.deleteMisura(idMisura);
	}
	
	
	//------ ATTUATORE ------
	public Attuatore getAttuatoreId(int idAttuatore) {
		Attuatore attuatore = this.daoAttuatore.getAttuatoreId(idAttuatore);
		HashSet<Attivazione> attivazioni = this.daoAttivazioni.getAttivazioniAttuatore(idAttuatore);
		
		if (attivazioni != null) {
			attuatore.setAttivazioni(attivazioni);
		}
		
		return attuatore;
	}
	
	
	public HashSet<Attuatore> getAttuatoriCampo(int idCampo) {
		HashSet<Attuatore> attuatori = this.daoAttuatore.getAttuatoriCampo(idCampo);
		
		if (attuatori != null) {
			for (Attuatore attuatore : attuatori) {
				HashSet<Attivazione> attivazioni = this.daoAttivazioni.getAttivazioniAttuatore(attuatore.getId());
				
				if (attivazioni != null) {
					attuatore.setAttivazioni(attivazioni);
				}
			}
		}
		
		return attuatori;
	}
	
	
	public int addAttuatore(Attuatore attuatore) {
		int id = this.daoAttuatore.addAttuatore(attuatore);
		
		this.daoAttivazioni.addAttivazione(
				new Attivazione(false, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
						id));
		
		return id;
	}
	
	
	public void deleteAttuatore(int idAttuatore) {
		this.daoAttuatore.deleteAttuatore(idAttuatore);
	}
	
	
	//------ ATTIVAZIONE ------
	public Attivazione getAttivazioneId(int idAttivazione) {
		return this.daoAttivazioni.getAttivazioneId(idAttivazione);
	}
	
	
	public HashSet<Attivazione> getAttivazioniAttuatore(int idAttuatore) {
		return this.daoAttivazioni.getAttivazioniAttuatore(idAttuatore);
	}
	
	
	public Integer addAttivazione(Attivazione attivazione) {
		return this.daoAttivazioni.addAttivazione(attivazione);
	}
	
	
	public void deleteAttivazione(int idAttivazione) {
		this.daoAttivazioni.deleteAttivazione(idAttivazione);
	}
	
	
	//------ RICHIESTA ------
	public RichiestaIdrica getRichiestaId(int idRichiesta) {
		RichiestaIdrica richiestaIdrica = this.daoRichieste.getRichiestaId(idRichiesta);
		
		if (richiestaIdrica != null) {
			Approvazione approvazione = this.daoApprovazione.getApprovazioneIdRichiesta(idRichiesta);
			if (approvazione != null) {
				richiestaIdrica.setApprovato(approvazione);
			}
		}
		
		return richiestaIdrica;
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteColtivazione(int idColtivazione) {
		return this.daoRichieste.getRichiesteColtivazione(idColtivazione);
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteBacino(int idBacino) {
		return this.daoRichieste.getRichiesteBacino(idBacino);
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteAzienda(String idAzienda) {
		HashSet<RichiestaIdrica> richieste = this.daoRichieste.getRichiesteAzienda(idAzienda);
		
		if (richieste != null) {
			for (RichiestaIdrica richiestaIdrica : richieste) {
				if (richiestaIdrica != null) {
					Approvazione approvazione =
							this.daoApprovazione.getApprovazioneIdRichiesta(richiestaIdrica.getId());
					if (approvazione != null) {
						richiestaIdrica.setApprovato(approvazione);
					}
				}
			}
		}
		
		return richieste;
	}
	
	
	public int addRichiesta(RichiestaIdrica richiesta) {
		return this.daoRichieste.addRichiesta(richiesta);
	}
	
	
	public void deleteRichiesta(int idRichiesta) {
		this.daoRichieste.deleteRichiesta(idRichiesta);
	}
	
	
	//------ APPROVAZIONE ------
	public Approvazione getApprovazioneId(int idApprovazione) {
		return this.daoApprovazione.getApprovazioneIdRichiesta(idApprovazione);
	}
	
	
	public HashSet<Approvazione> getApprovazioniGestore(int idGestore) {
		return this.daoApprovazione.getApprovazioniGestore(idGestore);
	}
	
	
	public void addApprovazione(Approvazione approvazione) {
		RichiestaIdrica richiesta = this.daoRichieste.getRichiestaId(approvazione.getIdRichiesta());
		RisorsaIdrica risorsa = new RisorsaIdrica();
		RisorsaIdrica lastBacino = this.daoRisorseBacino.ultimaRisorsa(richiesta.getIdBacino());
		RisorsaIdrica lastAzienda = this.daoRisorseAzienda.ultimaRisorsa(
				this.daoAzienda.getAziendaNome(richiesta.getNomeAzienda()).getId());
		RisorsaIdrica newAzienda = new RisorsaIdrica();
		
		newAzienda.setData(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
		
		risorsa.setData(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
		risorsa.setConsumo(lastBacino.getConsumo() + richiesta.getQuantita());
		risorsa.setDisponibilita(lastBacino.getDisponibilita() - richiesta.getQuantita());
		risorsa.setIdSource(richiesta.getIdBacino());
		
		this.daoRisorseBacino.addRisorsaBacino(risorsa);
		
		this.daoApprovazione.addApprovazione(approvazione);
	}
	
	
	public void deleteApprovazione(int idApprovazione) {
		this.daoApprovazione.deleteApprovazione(idApprovazione);
	}
	
	
	//------ RISORSEAZIENDA ------
	public RisorsaIdrica getRisorsaAziendaId(int idRisorsa) {
		return this.daoRisorseAzienda.getRisorsaAziendaId(idRisorsa);
	}
	
	
	public HashSet<RisorsaIdrica> getStoricoRisorseAzienda(int idAzienda) {
		return this.daoRisorseAzienda.getStoricoRisorseAzienda(idAzienda);
	}
	
	
	public int addRisorsaAzienda(RisorsaIdrica risorsaIdrica) {
		return this.daoRisorseAzienda.addRisorsaAzienda(risorsaIdrica);
	}
	
	
	public void deleteRisorsaAzienda(int uuid) {
		this.daoRisorseAzienda.deleteRisorsaAzienda(uuid);
	}
	
	
	//------ RISORSEBACINO ------
	public RisorsaIdrica getRisorsaBacinoId(int idRisorsa) {
		return this.daoRisorseBacino.getRisorsaBacinoId(idRisorsa);
	}
	
	
	public HashSet<RisorsaIdrica> getStoricoRisorseBacino(int idBacino) {
		return this.daoRisorseBacino.getStoricoRisorseBacino(idBacino);
	}
	
	
	public int addRisorsaBacino(RisorsaIdrica risorsaIdrica) {
		return this.daoRisorseBacino.addRisorsaBacino(risorsaIdrica);
	}
	
	
	public void deleteRisorsaBacino(int id) {
		this.daoRisorseBacino.deleteRisorsaBacino(id);
	}
	
	
	//
	public HashSet<String> getRaccolti() {
		return this.daoUtils.getRaccolti();
	}
	
	
	public void addRaccolto(String raccolto) {
		this.daoUtils.addRaccolto(raccolto);
	}
	
	
	public void deleteRaccolto(String nome) {
		this.daoUtils.deleteRaccolto(nome);
	}
	
	
	public HashSet<String> getEsigenze() {
		return this.daoUtils.getEsigenze();
	}
	
	
	public void addEsigenza(String esigenza) {
		this.daoUtils.addEsigenza(esigenza);
	}
	
	
	public void deleteEsigenza(String nome) {
		this.daoUtils.deleteEsigenza(nome);
	}
	
	
	public HashSet<String> getIrrigazioni() {
		return this.daoUtils.getIrrigazioni();
	}
	
	
	public void addIrrigazione(String nome) {
		this.daoUtils.addIrrigazione(nome);
	}
	
	
	public void deleteIrrigazione(String nome) {
		this.daoUtils.deleteIrrigazione(nome);
	}
	
	
	public boolean existsByUsername(String username) {
		return ! this.daoUser.existsUsername(username);
	}
	
	
	public boolean existsByEmail(String email) {
		return this.daoUser.existsMail(email);
	}
	
	
	public RisorsaIdrica ultimaRisorsaBacino(int idBacino) {
		return this.daoRisorseBacino.ultimaRisorsa(idBacino);
	}
	
	
	public RisorsaIdrica ultimaRisorsaAzienda(int idAzienda) {
		return this.daoRisorseAzienda.ultimaRisorsa(idAzienda);
	}
	
	
	public HashSet<String> getSensorTypes() {
		return this.daoUtils.getSensorTypes();
	}
	
	
	public void addSensorType(String nome) {
		this.daoUtils.addSensorType(nome);
	}
	
	
	public void deleteSensorType(String nome) {
		this.daoUtils.deleteSensorType(nome);
	}
	
	
	private int countGestoriAzienda() {
		return this.daoUtils.countGestori(UserRole.GESTOREAZIENDA);
	}
	
	
	private int countGestoriBacino() {
		return this.daoUtils.countGestori(UserRole.GESTOREIDRICO);
	}
	
	
	private int countRaccolti() {
		return this.daoUtils.countRaccolti();
	}
	
	
	private int countEsigenze() {
		return this.daoUtils.countEsigenze();
	}
	
	
	private int countIrrigazioni() {
		return this.daoUtils.countIrrigazioni();
	}
	
	
	private int countSensorTypes() {
		return this.daoUtils.countSensorTypes();
	}
	
	
	private int countCampi() {
		return this.daoUtils.countCampi();
	}
	
	
	private int countCampagne() {
		return this.daoUtils.countCampagne();
	}
	
	
	private int countAziende() {
		return this.daoUtils.countAziende();
	}
	
	
	private int countBacini() {
		return this.daoUtils.countBacini();
	}
	
	
	public ElementsCount getcount() {
		ElementsCount count = new ElementsCount();
		
		count.setGestoriAzienda(this.countGestoriAzienda());
		count.setGestoriBacino(this.countGestoriBacino());
		count.setAziende(this.countAziende());
		count.setCampagne(this.countCampagne());
		count.setCampi(this.countCampi());
		count.setBacini(this.countBacini());
		count.setSensorTypes(this.countSensorTypes());
		count.setRaccolti(this.countRaccolti());
		count.setEsigenze(this.countEsigenze());
		count.setIrrigazioni(this.countIrrigazioni());
		
		return count;
	}
	
	
	public HashSet<GestoreIdrico> getGestoriIdrici() {
		return this.daoUser.getGestoriIdrici();
	}
	
	
	public HashSet<GestoreAzienda> getGestoriAzienda() {
		return this.daoUser.getGestoriAzienda();
	}
	
	
	public HashSet<Azienda> getAllAziende() {
		return this.daoAzienda.getAziende();
	}
	
	
	public HashSet<Campagna> getAllCampagne() {
		return this.daoCampagna.getCampagne();
	}
	
	
	public HashSet<Campo> getAllCampi() {
		return this.daoCampo.getCampi();
	}
	
	
	public boolean existsCampagnaAzienda(int id, String campagna) {
		return this.daoCampagna.existsCampagnaAzienda(id, campagna);
	}
	
	
	public boolean existsCampoCampagna(int id, String campo) {
		return this.daoCampo.existsCampoCampagna(id, campo);
	}
	
}
