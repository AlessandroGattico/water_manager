package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Approvazione;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoApprovazione {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	private final String archive =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/ARCHIVE";
	private static final Logger logger = LogManager.getLogger(DaoApprovazione.class.getName());
	private static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private Connection connectionTgt;
	
	
	protected DaoApprovazione() {
		this.connectionTgt = null;
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
					approvazione = new Approvazione(
							resultSet.getInt("id"),
							resultSet.getInt("id_richiesta"),
							resultSet.getInt("id_gestore"),
							resultSet.getBoolean("approvato"),
							resultSet.getString("date")
					);
					
					logger.debug("Trovata approvazione: {} per la richiesta con ID: {}", approvazione.getId(),
							idRichiesta);
				} else {
					logger.info("Nessuna approvazione trovata per la richiesta con ID: {}", idRichiesta);
				}
				
				return approvazione;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'approvazione per la richiesta con ID: {}", idRichiesta, e);
			
			return approvazione;
		}
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
		Connection connection = null;
		
		String query = """
				DELETE FROM approvazione
				WHERE id = ? ;
				""";
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Tentativo di eliminazione del raccolto: {}", idApprovazione);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idApprovazione);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Raccolto '{}' eliminato con successo.", idApprovazione);
				} else {
					logger.info("Nessun raccolto trovato con nome '{}'.", idApprovazione);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione del raccolto '{}'", idApprovazione, e);
			
			if (connection != null) {
				try {
					connection.rollback();
					
					logger.info("Rollback eseguito a seguito di un errore.");
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
			
			throw new RuntimeException("Errore durante l'eliminazione del raccolto", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
					
					logger.info("Connessione chiusa.");
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
	}
	
	
	public void clearApprovazioni() {
		int totalMonths = 12 + LocalDateTime.now().getMonthValue() - 1;
		
		LocalDateTime result = LocalDateTime.now()
				.minusMonths(totalMonths)
				.with(TemporalAdjusters.firstDayOfMonth());
		
		String retention = result.format(formatterData);
		
		Archive archive = new Archive(this.url, this.archive, "approvazione", retention);
		
		archive.export();
	}
	
	
}
