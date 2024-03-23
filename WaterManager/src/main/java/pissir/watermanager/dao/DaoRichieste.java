package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.controller.ControllerAdmin;
import pissir.watermanager.model.item.RichiestaIdrica;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoRichieste {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	public static final Logger logger = LogManager.getLogger(DaoRichieste.class.getName());
	
	
	public DaoRichieste() {
	}
	
	
	public RichiestaIdrica getRichiestaId(int idRichiesta) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		RichiestaIdrica richiestaIdrica = null;
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idRichiesta);
			
			logger.info("Esecuzione della query per ottenere la richiesta idrica con ID: {}", idRichiesta);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					richiestaIdrica = new RichiestaIdrica((int) row.get("id"), (Double) row.get("quantita"),
							(int) row.get("id_coltivazione"), (int) row.get("id_bacino"),
							(String) row.get("date"), (String) row.get("nome_azienda"));
					
					logger.debug("Trovata richiesta idrica: {}", richiestaIdrica.getId());
				} else {
					logger.info("Nessuna richiesta idrica trovata con ID: {}", idRichiesta);
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della richiesta idrica con ID: {}", idRichiesta, e);
			
			return null;
		}
		
		return richiestaIdrica;
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteColtivazione(int idColtivazione) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RichiestaIdrica> richieste = new HashSet<>();
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id_coltivazione = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idColtivazione);
			
			logger.info("Esecuzione della query per ottenere le richieste della coltivazione con ID: {}",
					idColtivazione);
			
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
					RichiestaIdrica richiestaIdrica =
							new RichiestaIdrica((int) map.get("id"), (Double) map.get("quantita"),
									(int) map.get("id_coltivazione"), (int) map.get("id_bacino"),
									(String) map.get("date"), (String) map.get("nome_azienda"));
					
					richieste.add(richiestaIdrica);
				}
				
				logger.debug("Richieste idriche trovate per la coltivazione con ID {}: {}", idColtivazione,
						richieste.size());
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle richieste idriche per la coltivazione con ID {}",
					idColtivazione, e);
			
			return null;
		}
		
		return richieste;
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteBacino(int idBacino) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RichiestaIdrica> richieste = new HashSet<>();
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id_bacino = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idBacino);
			
			logger.info("Esecuzione della query per ottenere le richieste del bacino con ID: {}", idBacino);
			
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
					RichiestaIdrica richiestaIdrica =
							new RichiestaIdrica((int) map.get("id"), (Double) map.get("quantita"),
									(int) map.get("id_coltivazione"), (int) map.get("id_bacino"),
									(String) map.get("date"), (String) map.get("nome_azienda"));
					
					richieste.add(richiestaIdrica);
				}
				
				logger.debug("Richieste idriche trovate per il bacino con ID {}: {}", idBacino, richieste.size());
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle richieste idriche per il bacino con ID {}", idBacino, e);
			
			return null;
		}
		
		return richieste;
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteAzienda(String idAzienda) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RichiestaIdrica> richieste = new HashSet<>();
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE nome_azienda = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, idAzienda);
			
			logger.info("Esecuzione della query per ottenere le richieste dell'azienda con nome: {}", idAzienda);
			
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
					RichiestaIdrica richiestaIdrica =
							new RichiestaIdrica((int) map.get("id"), (Double) map.get("quantita"),
									(int) map.get("id_coltivazione"), (int) map.get("id_bacino"),
									(String) map.get("date"), (String) map.get("nome_azienda"));
					
					richieste.add(richiestaIdrica);
				}
				
				logger.debug("Richieste idriche trovate per l'azienda '{}': {}", idAzienda, richieste.size());
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle richieste idriche per l'azienda '{}'", idAzienda, e);
			
			return null;
		}
		
		return richieste;
	}
	
	
	public int addRichiesta(RichiestaIdrica richiesta) {
		int id = 0;
		String query = """
				INSERT INTO richiesta (quantita, id_coltivazione, id_bacino, date, nome_azienda)
				VALUES (?, ?, ?, ?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setDouble(1, richiesta.getQuantita());
				statement.setInt(2, richiesta.getIdColtivazione());
				statement.setInt(3, richiesta.getIdBacino());
				statement.setString(4, richiesta.getDate());
				statement.setString(5, richiesta.getNomeAzienda());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
					}
				}
				
				connection.commit();
				
				logger.info("Richiesta idrica aggiunta con successo: {}", id);
			}
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante il rollback", ex);
				}
			}
			
			logger.error("Errore durante l'aggiunta della richiesta idrica", e);
			
			throw new RuntimeException("Errore durante l'aggiunta della richiesta idrica", e);
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
	
	
	public void deleteRichiesta(int idRichiesta) {
		String query = """
				DELETE FROM richiesta
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idRichiesta);
				
				int affectedRows = statement.executeUpdate();
				connection.commit();
				
				if (affectedRows > 0) {
					logger.info("Richiesta idrica con ID {} eliminata con successo.", idRichiesta);
				} else {
					logger.info("Nessuna richiesta idrica trovata con ID {} per l'eliminazione.", idRichiesta);
				}
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante il rollback", ex);
				}
			}
			
			logger.error("Errore durante l'eliminazione della richiesta idrica con ID {}", idRichiesta, e);
			
			throw new RuntimeException("Errore durante l'eliminazione della richiesta idrica", e);
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
