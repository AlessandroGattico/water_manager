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
	
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/DATABASEWATER";
	private final String archive = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/ARCHIVE";
	
	private static final Logger logger = LogManager.getLogger(DaoApprovazione.class.getName());
	private static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	protected DaoApprovazione() {
	}
	
	
	protected Approvazione getApprovazioneIdRichiesta(int idRichiesta) {
		Approvazione approvazione = null;
		String query = """
				SELECT *
				FROM approvazione
				WHERE id_richiesta = ? ;
				""";
		
		logger.info("Estrazione approvazione con id_richiesta: {}", idRichiesta);
		
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
		
		logger.info("Estrazione approvazione per richiesta: {}", approvazione.getIdRichiesta());
		
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
			logger.error("Errore durante l'aggiunta dell'approvazione", e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
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
		
		logger.info("Eliminazione approvazione con ID: {}", idApprovazione);
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Eliminazione dell'approvazione con ID: {}", idApprovazione);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idApprovazione);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Approvazione con ID: {} eliminata con successo.", idApprovazione);
				} else {
					logger.info("Nessuna approvazione trovata con id {}.", idApprovazione);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione dell'approvazione {}", idApprovazione, e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
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
		
		logger.info("Preparazione per l'archiviazione delle approvazioni prima di: {}", retention);
		
		Archive archive = new Archive(this.url, this.archive, "approvazione", retention);
		
		try {
			archive.export();
			
			logger.info("Archiviazione completata con successo.");
		} catch (Exception e) {
			logger.error("Errore durante l'archiviazione delle approvazioni", e);
		}
	}
	
	
}
