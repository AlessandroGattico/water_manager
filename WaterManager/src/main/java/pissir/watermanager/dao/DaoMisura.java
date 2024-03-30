package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Misura;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
	private static final Logger logger = LogManager.getLogger(DaoMisura.class.getName());
	
	
	protected DaoMisura() {
	}
	
	
	protected Misura getMisuraId(int idMisura) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
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
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					misura = new Misura((int) row.get("id"), (Double) row.get("value"), (String) row.get("time"),
							(int) row.get("id_sensore"));
					
					logger.debug("Trovata misura: ID {} con valore {}", misura.getId(), misura.getValue());
				} else {
					logger.info("Nessuna misura trovata con ID: {}", idMisura);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della misura con ID: {}", idMisura, e);
			
			return null;
		}
		
		return misura;
	}
	
	
	protected HashSet<Misura> getMisureSensore(int idSensore) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Misura> misure = new HashSet<>();
		
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
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					Misura misura = new Misura((int) row.get("id"), (Double) row.get("value"),
							(String) row.get("time"), (int) row.get("id_sensore"));
					
					misure.add(misura);
				}
				
				if (misure.isEmpty()) {
					logger.info("Nessuna misura trovata per il sensore con ID: {}", idSensore);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle misure per il sensore con ID: {}", idSensore, e);
			
			return null;
		}
		
		return misure;
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
			try {
				if (connection != null) {
					connection.rollback();
				}
				
				logger.error("Errore durante l'eliminazione della misura con ID {}", idMisura, e);
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
	
}
