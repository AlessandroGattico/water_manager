package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Approvazione;

import java.sql.*;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoApprovazione {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	private static final Logger logger = LogManager.getLogger(DaoApprovazione.class.getName());
	
	
	public DaoApprovazione() {
	}
	
	
	protected Approvazione getApprovazioneIdRichiesta(int idRichiesta) {
		Approvazione approvazione = null;
		
		String query = """
				SELECT *
				FROM approvazione
				WHERE id_richiesta = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idRichiesta);
			
			logger.info("Estrazione approvazione con ID richiesta: {}", idRichiesta);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				
				if (resultSet.next()) {
					approvazione = new Approvazione(resultSet.getInt("id"), resultSet.getInt("id_richiesta"),
							resultSet.getInt("id_gestore"), resultSet.getBoolean("approvato"),
							resultSet.getString("date"));
					
					logger.debug("Trovata approvazione: {} per la richiesta con ID: {}", approvazione.getId(),
							idRichiesta);
				} else {
					logger.info("Nessuna approvazione trovata per la richiesta con ID: {}", idRichiesta);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'approvazione per la richiesta con ID: {}", idRichiesta, e);
			return null;
		}
		
		return approvazione;
	}
	
	
	protected HashSet<Approvazione> getApprovazioniGestore(int idGestore) {
		HashSet<Approvazione> approvazioni = new HashSet<>();
		String query = """
				SELECT *
				FROM approvazione
				WHERE id_gestore = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idGestore);
			logger.info("Esecuzione della query per trovare le approvazioni del gestore con ID: {}", idGestore);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Approvazione approvazione =
							new Approvazione(resultSet.getInt("id"), resultSet.getInt("id_richiesta"),
									resultSet.getInt("id_gestore"), resultSet.getBoolean("approvato"),
									resultSet.getString("date"));
					
					approvazioni.add(approvazione);
				}
				logger.debug("Trovate {} approvazioni per il gestore con ID: {}", approvazioni.size(), idGestore);
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle approvazioni per il gestore con ID: {}", idGestore, e);
			
			return null;
		}
		
		return approvazioni;
	}
	
	
	protected int addApprovazione(Approvazione approvazione) {
		Connection connection = null;
		int id = 0;
		String query = """
				INSERT INTO approvazione (id_richiesta, id_gestore, approvato, date)
				VALUES (?, ?, ?, ?);
				""";
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setInt(1, approvazione.getIdRichiesta());
				statement.setInt(2, approvazione.getIdGestore());
				statement.setBoolean(3, approvazione.isApprovato());
				statement.setString(4, approvazione.getDate());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
						logger.info("Approvazione inserita con ID: {}", id);
					}
				}
			}
			connection.commit();
			
			logger.info("Approvazione inserita con ID: {}", id);
		} catch (Exception e) {
			logger.error("Errore durante la connessione al database per aggiungere l'approvazione", e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
			
			throw new RuntimeException("Errore durante l'aggiunta dell'approvazione", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
		
		return id;
	}
	
	
	public void deleteApprovazione(int idApprovazione) {
		String query = """
				DELETE FROM approvazione
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Inizio cancellazione dell'approvazione con ID {}", idApprovazione);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idApprovazione);
				
				int affectedRows = statement.executeUpdate();
				
				
				connection.commit();
				
				logger.info("Cancellazione dell'approvazione con ID {} completata con successo ",
						idApprovazione);
			} catch (SQLException e) {
				connection.rollback();
				
				logger.error("Errore durante la cancellazione dell'approvazione, rollback effettuato", e);
				
				throw e;
			}
		} catch (Exception e) {
			logger.error("Errore durante l'apertura della connessione per la cancellazione dell'approvazione", e);
			
			throw new RuntimeException("Errore durante la cancellazione dell'approvazione", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore durante la chiusura della connessione", e);
				}
			}
		}
	}
	
	
}
