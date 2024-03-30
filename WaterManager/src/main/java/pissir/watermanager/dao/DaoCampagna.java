package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Campagna;

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
public class DaoCampagna {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	private static final Logger logger = LogManager.getLogger(DaoCampagna.class.getName());
	
	
	protected DaoCampagna() {
		
	}
	
	
	protected Campagna getCampagnaId(int id) {
		Campagna campagna = null;
		
		String query = """
				SELECT *
				FROM campagna
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			
			logger.info("Esecuzione della query per ottenere la campagna con ID: {}", id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					campagna = new Campagna(resultSet.getInt("id"), resultSet.getString("nome"),
							resultSet.getInt("id_azienda"));
					
					logger.debug("Trovata campagna: {}", campagna.getNome());
				} else {
					logger.info("Nessuna campagna trovata con ID: {}", id);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della campagna con ID: {}", id, e);
			
			return null;
		}
		
		return campagna;
	}
	
	
	protected HashSet<Campagna> getCampagnaAzienda(int idAzienda) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Campagna> campagne = new HashSet<>();
		
		String query = """
				SELECT *
				FROM campagna
				WHERE id_azienda = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idAzienda);
			
			logger.info("Esecuzione della query per ottenere le campagne dell'azienda con ID: {}", idAzienda);
			
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
					logger.info("Nessuna campagna trovata per l'azienda con ID: {}", idAzienda);
				}
				
				for (HashMap<String, Object> map : list) {
					Campagna campagna =
							new Campagna((int) map.get("id"), (String) map.get("nome"), (int) map.get("id_azienda"));
					
					campagne.add(campagna);
					
					logger.debug("Aggiunta campagna: {}", campagna.getNome());
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle campagne per l'azienda con ID: {}", idAzienda, e);
			
			return campagne;
		}
		
		return campagne;
	}
	
	
	protected int addCampagna(Campagna campagna) {
		int id = 0;
		Connection connection = null;
		
		String query = """
				INSERT INTO campagna (nome, id_azienda)
				VALUES (?, ?);
				""";
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, campagna.getNome());
				statement.setInt(2, campagna.getIdAzienda());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys();) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
					}
				}
			}
			connection.commit();
			
			logger.info("Campagna aggiunta con ID {}", id);
			
		} catch (Exception e) {
			logger.error("Errore durante l'aggiunta della campagna", e);
			
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
			
			throw new RuntimeException("Errore durante l'aggiunta della campagna", e);
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
	
	
	protected void deleteCampagna(int id) {
		String query = """
				DELETE FROM campagna
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setLong(1, id);
				
				logger.info("Tentativo di eliminare la campagna con ID: {}", id);
				
				statement.executeUpdate();
				
				logger.info("Campagna con ID {} eliminata con successo", id);
				
				connection.commit();
			} catch (SQLException e) {
				if (connection != null) {
					connection.rollback();
					
					logger.error("Rollback eseguito a causa di un errore durante l'eliminazione della campagna", e);
				}
				throw e;
			}
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione della campagna", e);
			
			throw new RuntimeException("Errore durante l'eliminazione della campagna", e);
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
	
	
	protected HashSet<Campagna> getCampagne() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Campagna> campagne = new HashSet<>();
		
		String query = "SELECT * FROM campagna;";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			logger.info("Esecuzione della query per ottenere tutte le campagne");
			
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
					Campagna campagna =
							new Campagna((int) map.get("id"), (String) map.get("nome"), (int) map.get("id_azienda"));
					
					campagne.add(campagna);
				}
				
				logger.debug("Numero totale di campagne trovate: {}", campagne.size());
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle campagne", e);
			
			return campagne;
		}
		
		return campagne;
	}
	
	
	protected boolean existsCampagnaAzienda(int id, String campagna) {
		String sql = """
				SELECT COUNT(*)
				FROM campagna
				WHERE nome = ?
				AND id_azienda = ?;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, campagna);
			statement.setInt(2, id);
			
			logger.info("Verifica esistenza della campagna '{}' per l'azienda con ID {}", campagna, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
				boolean exists = rs.getInt(1) > 0;
				
				logger.debug("La campagna '{}' {} nell'azienda con ID {}", campagna, exists ? "esiste" : "non esiste",
						id);
				
				return exists;
			}
		} catch (SQLException e) {
			logger.error("Errore durante la verifica dell'esistenza della campagna '{}' per l'azienda con ID {}",
					campagna, id, e);
		}
		
		return false;
	}
	
}
