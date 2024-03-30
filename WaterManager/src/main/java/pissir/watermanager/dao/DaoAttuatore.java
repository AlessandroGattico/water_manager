package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Attuatore;

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
public class DaoAttuatore {
	
	private static final Logger logger = LogManager.getLogger(DaoAttuatore.class.getName());
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	protected DaoAttuatore() {
	
	}
	
	
	protected Attuatore getAttuatoreId(int uuidAttuatore) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		Attuatore attuatore = null;
		
		String query = """
				SELECT *
				FROM attuatore
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, uuidAttuatore);
			
			logger.info("Recupero dell'attuatore con ID {}", uuidAttuatore);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					attuatore = new Attuatore((int) row.get("id"), (String) row.get("nome"), (int) row.get("id_campo"));
				}
			}
			
			if (attuatore != null) {
				logger.info("Attuatore trovato: {}", attuatore);
			} else {
				logger.warn("Nessun attuatore trovato con ID {}", uuidAttuatore);
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'attuatore con ID {}", uuidAttuatore, e);
			
			return null;
		}
		
		return attuatore;
	}
	
	
	protected HashSet<Attuatore> getAttuatoriCampo(long uuidCampo) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Attuatore> attuatori = new HashSet<>();
		
		String query = """
				SELECT *
				FROM attuatore
				WHERE id_campo = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, uuidCampo);
			
			logger.info("Recupero degli attuatori per il campo con ID {}", uuidCampo);
			
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
					Attuatore attuatore =
							new Attuatore((int) map.get("id"), (String) map.get("nome"), (int) map.get("id_campo"));
					
					attuatori.add(attuatore);
				}
				
				logger.info("Numero di attuatori trovati: {}", attuatori.size());
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero degli attuatori per il campo con ID {}", uuidCampo, e);
			
			return null;
		}
		
		return attuatori;
	}
	
	
	protected int addAttuatore(Attuatore attuatore) {
		Connection connection = null;
		int id = 0;
		String query = """
				INSERT INTO attuatore (nome, id_campo)
				VALUES (?, ?);
				""";
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, attuatore.getNome());
				statement.setInt(2, attuatore.getIdCampo());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
					}
				}
			}
			
			connection.commit();
			
			logger.info("Attuatore aggiunto con successo con ID: {}", id);
		} catch (Exception e) {
			logger.error("Errore durante l'inserimento dell'attuatore", e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
			
			throw new RuntimeException("Errore durante l'aggiunta dell'attuatore", e);
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
	
	
	protected void deleteAttuatore(int id) {
		String query = """
				DELETE FROM attuatore
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, id);
				
				int rowsAffected = statement.executeUpdate();
				if (rowsAffected > 0) {
					logger.info("Attuatore con ID {} eliminato con successo", id);
				} else {
					logger.warn("Nessun attuatore trovato con ID {}", id);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
				logger.error("Errore durante l'eliminazione dell'attuatore", e);
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback dell'eliminazione dell'attuatore", ex);
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
