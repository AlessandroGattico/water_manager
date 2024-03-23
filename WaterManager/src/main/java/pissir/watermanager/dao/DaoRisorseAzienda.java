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
 * @author alessandrogattico
 */

@Repository
public class DaoRisorseAzienda {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	public static final Logger logger = LogManager.getLogger(DaoRisorseAzienda.class.getName());
	
	
	public DaoRisorseAzienda() {
	}
	
	
	public RisorsaIdrica getRisorsaAziendaId(int idRisorsa) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		RisorsaIdrica risorsaIdrica = null;
		
		String query = """
				SELECT *
				FROM risorsa_azienda
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idRisorsa);
			
			logger.info("Esecuzione della query per ottenere la risorsa idrica aziendale con ID: {}", idRisorsa);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					risorsaIdrica = new RisorsaIdrica(
							(int) row.get("id"), (String) row.get("time"),
							(Double) row.get("disponibilita"), (Double) row.get("consumo"),
							(int) row.get("id_azienda"));
					
				} else {
					logger.info("Nessuna risorsa idrica aziendale trovata con ID: {}", idRisorsa);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della risorsa idrica aziendale con ID: {}", idRisorsa, e);
			
			return null;
		}
		
		return risorsaIdrica;
	}
	
	
	public HashSet<RisorsaIdrica> getStoricoRisorseAzienda(int idAzienda) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RisorsaIdrica> risorse = new HashSet<>();
		
		String query = """
				SELECT *
				FROM risorsa_azienda
				WHERE id_azienda = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idAzienda);
			
			logger.info(
					"Esecuzione della query per ottenere lo storico delle risorse idriche aziendali con ID azienda: {}",
					idAzienda);
			
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
				
				for (HashMap<String, Object> map : list) {
					RisorsaIdrica risorsaIdrica = new RisorsaIdrica(
							(int) map.get("id"), (String) map.get("time"),
							(Double) map.get("disponibilita"), (Double) map.get("consumo"),
							(int) map.get("id_azienda"));
					
					risorse.add(risorsaIdrica);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dello storico delle risorse idriche aziendali con ID azienda: {}",
					idAzienda, e);
			
			return null;
		}
		
		return risorse;
	}
	
	
	public int addRisorsaAzienda(RisorsaIdrica risorsaIdrica) {
		int id = 0;
		Connection connection = null;
		
		String query = """
				INSERT INTO risorsa_azienda (consumo, disponibilita, time, id_azienda)
				VALUES (?, ?, ?, ?);
				""";
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setDouble(1, risorsaIdrica.getConsumo());
				statement.setDouble(2, risorsaIdrica.getDisponibilita());
				statement.setString(3, risorsaIdrica.getData());
				statement.setInt(4, risorsaIdrica.getIdSource());
				
				logger.info("Esecuzione della query per aggiungere una nuova risorsa idrica aziendale");
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
					}
				}
			}
			
			connection.commit();
			
			logger.info("Risorsa idrica aziendale aggiunta con successo, ID: {}", id);
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback dell'aggiunta della risorsa idrica aziendale", ex);
			}
			
			logger.error("Errore durante l'aggiunta della risorsa idrica aziendale", e);
			
			throw new RuntimeException("Errore durante l'aggiunta della risorsa idrica aziendale", e);
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
	
	
	public void deleteRisorsaAzienda(int id) {
		Connection connection = null;
		String query = """
				DELETE FROM risorsa_azienda
				WHERE id = ? ;
				""";
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, id);
				
				logger.info("Esecuzione della query per eliminare la risorsa idrica aziendale con ID: {}", id);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.info("Risorsa idrica aziendale eliminata con successo");
				} else {
					logger.info("Nessuna risorsa idrica aziendale trovata con ID: {}", id);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback della cancellazione della risorsa idrica aziendale", ex);
			}
			
			logger.error("Errore durante la cancellazione della risorsa idrica aziendale", e);
			
			throw new RuntimeException("Errore durante la cancellazione della risorsa idrica aziendale", e);
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
	
	
	public RisorsaIdrica ultimaRisorsa(int idAzienda) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		RisorsaIdrica risorsa = null;
		
		String query = """
				SELECT * FROM risorsa_azienda
				WHERE id_azienda = ?
				ORDER BY time DESC LIMIT 1;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idAzienda);
			
			logger.info("Esecuzione della query per ottenere l'ultima risorsa idrica aziendale con ID azienda: {}",
					idAzienda);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					risorsa = new RisorsaIdrica(
							(int) row.get("id"),
							(String) row.get("time"),
							(Double) row.get("disponibilita"),
							(Double) row.get("consumo"),
							idAzienda
					);
					
					logger.debug("Trovata ultima risorsa idrica aziendale: {}", risorsa);
				} else {
					logger.info("Nessuna risorsa idrica aziendale trovata per l'ID azienda: {}", idAzienda);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'ultima risorsa idrica aziendale per l'ID azienda: {}",
					idAzienda, e);
			
			return null;
		}
		
		return risorsa;
	}
	
	
}
