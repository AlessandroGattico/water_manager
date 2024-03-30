package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.controller.ControllerAdmin;
import pissir.watermanager.model.item.RisorsaIdrica;

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
public class DaoRisorseBacino {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	private static final Logger logger = LogManager.getLogger(DaoRisorseBacino.class.getName());
	
	
	protected DaoRisorseBacino() {
	}
	
	
	protected RisorsaIdrica getRisorsaBacinoId(int idRisorsa) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		RisorsaIdrica risorsaIdrica = null;
		
		String query = """
				SELECT *
				FROM risorsa_bacino
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idRisorsa);
			
			logger.info("Esecuzione della query per ottenere la risorsa idrica del bacino con ID: {}", idRisorsa);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					risorsaIdrica = new RisorsaIdrica(
							(int) row.get("id"),
							(String) row.get("time"),
							(Double) row.get("disponibilita"),
							(Double) row.get("consumo"),
							(int) row.get("id_bacino")
					);
					
					logger.debug("Trovata risorsa idrica del bacino: {}", risorsaIdrica);
				} else {
					logger.info("Nessuna risorsa idrica del bacino trovata con ID: {}", idRisorsa);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della risorsa idrica del bacino con ID: {}", idRisorsa, e);
			
			return null;
		}
		
		return risorsaIdrica;
	}
	
	
	protected HashSet<RisorsaIdrica> getStoricoRisorseBacino(int idBacino) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RisorsaIdrica> risorse = new HashSet<>();
		
		String query = """
				SELECT *
				FROM risorsa_bacino
				WHERE id_bacino = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, idBacino);
			
			logger.info("Esecuzione della query per ottenere lo storico delle risorse idriche del bacino con ID: {}",
					idBacino);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				list = new ArrayList<>();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					list.add(row);
				}
				
				if (list.isEmpty()) {
					logger.info("Nessuna risorsa idrica trovata per il bacino con ID: {}", idBacino);
				}
				
				for (HashMap<String, Object> map : list) {
					RisorsaIdrica risorsaIdrica = new RisorsaIdrica(
							(int) map.get("id"),
							(String) map.get("time"),
							(Double) map.get("disponibilita"),
							(Double) map.get("consumo"),
							(int) map.get("id_bacino")
					);
					
					risorse.add(risorsaIdrica);
					logger.debug("Aggiunta risorsa idrica: {}", risorsaIdrica);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante la raccolta dello storico delle risorse idriche per il bacino con ID: {}",
					idBacino, e);
			return null;
		}
		
		return risorse;
	}
	
	
	protected int addRisorsaBacino(RisorsaIdrica risorsaIdrica) {
		int id = 0;
		
		String queryInsert = """
				INSERT INTO risorsa_bacino (consumo, disponibilita, time, id_bacino)
				VALUES (?, ?, ?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(queryInsert,
					Statement.RETURN_GENERATED_KEYS)) {
				statement.setDouble(1, risorsaIdrica.getConsumo());
				statement.setDouble(2, risorsaIdrica.getDisponibilita());
				statement.setString(3, risorsaIdrica.getData());
				statement.setInt(4, risorsaIdrica.getIdSource());
				
				logger.info("Inserimento di una nuova risorsa idrica per il bacino con ID: {}",
						risorsaIdrica.getIdSource());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
						logger.debug("Risorsa idrica aggiunta con ID: {}", id);
					}
				}
			}
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'inserimento di una nuova risorsa idrica", e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante il rollback", ex);
				}
			}
			
			return id;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore durante la chiusura della connessione", e);
				}
			}
		}
		
		return id;
	}
	
	
	protected void deleteRisorsaBacino(int id) {
		String query = """
				DELETE FROM risorsa_bacino
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setLong(1, id);
				
				int affectedRows = statement.executeUpdate();
				if (affectedRows > 0) {
					logger.info("Risorsa idrica con ID {} eliminata", id);
				} else {
					logger.warn("Nessuna risorsa idrica trovata con ID {}", id);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione della risorsa idrica con ID {}", id, e);
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante il rollback", ex);
				}
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
	
	
	protected RisorsaIdrica ultimaRisorsa(int idBacino) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		RisorsaIdrica risorsa = null;
		
		String query = """
				SELECT * FROM risorsa_bacino
				WHERE id_bacino = ?
				ORDER BY time
				DESC LIMIT 1;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idBacino);
			logger.info("Esecuzione della query per ottenere l'ultima risorsa idrica del bacino con ID {}", idBacino);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					risorsa = new RisorsaIdrica((int) row.get("id"), (String) row.get("time"),
							(Double) row.get("disponibilita"), (Double) row.get("consumo"),
							(int) row.get("id_bacino"));
					
					logger.debug("Trovata ultima risorsa idrica per il bacino con ID {}", idBacino);
				} else {
					logger.info("Nessuna risorsa idrica trovata per il bacino con ID {}", idBacino);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante la ricerca dell'ultima risorsa idrica per il bacino con ID {}", idBacino, e);
			
			return null;
		}
		
		return risorsa;
	}
	
	
}
