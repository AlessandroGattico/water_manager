package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Misura;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoMisura {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	private final String archive =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/ARCHIVE";
	private static final Logger logger = LogManager.getLogger(DaoMisura.class.getName());
	private static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	protected DaoMisura() {
	}
	
	
	protected Misura getMisuraId(int idMisura) {
		Misura misura = null;
		
		String query = """
				SELECT *
				FROM misura
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idMisura);
			
			logger.info("Esecuzione della query per ottenere la misura con ID: {}", idMisura);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					misura = new Misura(
							resultSet.getInt("id"),
							resultSet.getDouble("value"),
							resultSet.getString("time"),
							resultSet.getInt("id_sensore")
					);
					
					logger.debug("Trovata misura: ID {} con valore {}", misura.getId(), misura.getValue());
				} else {
					logger.info("Nessuna misura trovata con ID: {}", idMisura);
					
					return misura;
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della misura con ID: {}", idMisura, e);
			
			return misura;
		}
		
		return misura;
	}
	
	
	protected HashSet<Misura> getMisureSensore(int idSensore) {
		HashSet<Misura> misure = new HashSet<>();
		Misura misura = null;
		
		String query = """
				SELECT *
				FROM misura
				WHERE id_sensore = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idSensore);
			
			logger.info("Esecuzione della query per ottenere le misure del sensore con ID: {}", idSensore);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					misura = new Misura(
							resultSet.getInt("id"),
							resultSet.getDouble("value"),
							resultSet.getString("time"),
							resultSet.getInt("id_sensore")
					);
					
					misure.add(misura);
				}
				
				if (misure.isEmpty()) {
					logger.info("Nessuna misura trovata per il sensore con ID: {}", idSensore);
				} else {
					logger.info("Trovate {} misure per il sensore con ID: {}", misure.size(), idSensore);
				}
				return misure;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle misure per il sensore con ID: {}", idSensore, e);
			
			return misure;
		}
	}
	
	
	protected void addMisura(Misura misura) {
		String query = """
				INSERT INTO misura (value, time, id_sensore)
				VALUES (?, ?, ?);
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setDouble(1, misura.getValue());
				statement.setString(2, misura.getTime());
				statement.setInt(3, misura.getIdSensore());
				
				statement.executeUpdate();
				
				logger.debug("Misura aggiunta con successo: {}", misura);
				
				connection.commit();
			}
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
				
				logger.error("Errore durante l'aggiunta della misura: {}", misura, e);
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback", ex);
			}
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
	
	
	protected void deleteMisura(int idMisura) {
		String query = """
				DELETE FROM misura
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idMisura);
				
				int affectedRows = statement.executeUpdate();
				
				if (affectedRows > 0) {
					logger.debug("Misura con ID {} eliminata correttamente", idMisura);
				} else {
					logger.info("Nessuna misura trovata con ID {}", idMisura);
				}
				
				connection.commit();
			}
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione della coltivazione con ID: {}", idMisura, e);
			
			try {
				if (connection != null) {
					connection.rollback();
					
					logger.info("Eseguito rollback a seguito di un errore");
				}
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback dell'eliminazione della coltivazione", ex);
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore durante la chiusura della connessione al database", e);
				}
			}
		}
	}
	
	
	public void clearMisure() {
		int totalMonths = 12 + LocalDateTime.now().getMonthValue() - 1;
		
		LocalDateTime result = LocalDateTime.now()
				.minusMonths(totalMonths)
				.with(TemporalAdjusters.firstDayOfMonth());
		
		String retention = result.format(formatterData);
		
		Archive archive = new Archive(this.url, this.archive, "misura", retention);
		
		archive.export();
	}
	
}
