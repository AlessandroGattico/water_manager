package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Azienda;

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
public class DaoAzienda {
	
	private static final Logger logger = LogManager.getLogger(DaoAzienda.class.getName());
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	protected DaoAzienda() {
	}
	
	
	protected Azienda getAziendaId(int id) {
		Azienda azienda = null;
		
		String query = """
				SELECT *
				FROM azienda
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, id);
			logger.info("Esecuzione query per ottenere l'azienda con ID {}", id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					azienda = new Azienda(resultSet.getInt("id"), resultSet.getString("nome"),
							resultSet.getInt("id_user"));
					
					logger.info("Azienda trovata: {}", azienda.getNome());
				} else {
					logger.info("Nessuna azienda trovata con ID {}", id);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'azienda con ID {}", id, e);
			
			return null;
		}
		
		return azienda;
	}
	
	
	protected Azienda getAziendaNome(String nome) {
		Azienda azienda = null;
		
		String query = """
				SELECT *
				FROM azienda
				WHERE nome = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, nome);
			logger.info("Esecuzione query per ottenere l'azienda con nome {}", nome);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					azienda = new Azienda(resultSet.getInt("id"), resultSet.getString("nome"),
							resultSet.getInt("id_user"));
					
					logger.info("Azienda trovata: {}", azienda.getNome());
				} else {
					logger.info("Nessuna azienda trovata con nome {}", nome);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'azienda con nome {}", nome, e);
			
			return null;
		}
		
		return azienda;
	}
	
	
	protected HashSet<Azienda> getAziende() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Azienda> aziende = new HashSet<>();
		
		String query = """
				SELECT *
				FROM azienda;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			resultSetMetaData = resultSet.getMetaData();
			columns = resultSetMetaData.getColumnCount();
			list = new ArrayList<>();
			
			logger.info("Esecuzione della query per ottenere tutte le aziende");
			
			while (resultSet.next()) {
				row = new HashMap<>(columns);
				
				for (int i = 1; i <= columns; ++ i) {
					row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
				}
				
				list.add(row);
			}
			
			if (list.isEmpty()) {
				logger.info("Nessuna azienda trovata nel database");
			}
			
			for (HashMap<String, Object> map : list) {
				Azienda azienda = new Azienda((int) map.get("id"), (String) map.get("nome"), (int) map.get("id_user"));
				aziende.add(azienda);
				
				logger.debug("Aggiunta azienda: {}", azienda.getNome());
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle aziende", e);
			
			return null;
		}
		
		return aziende;
	}
	
	
	protected int addAzienda(Azienda azienda) {
		int id = 0;
		String query = """
				INSERT INTO azienda (nome, id_user)
				VALUES (?, ?);
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, azienda.getNome());
				statement.setInt(2, azienda.getIdGestore());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
					}
				}
			}
			connection.commit();
			
			logger.info("Azienda aggiunta con ID {}", id);
		} catch (Exception e) {
			logger.error("Errore durante l'aggiunta dell'azienda {}.", azienda.getNome(), e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
			
			throw new RuntimeException("Errore durante l'aggiunta dell'azienda", e);
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
	
	
	protected void deleteAzienda(int id) {
		String query = """
				DELETE FROM azienda
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Inizio transazione per cancellazione azienda con ID: {}", id);
			
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			statement.executeUpdate();
			connection.commit();
			
			logger.info("Transazione confermata e azienda con ID {} cancellata", id);
		} catch (SQLException e) {
			logger.error("Errore durante la cancellazione dell'azienda con ID {}. Effettuando il rollback...", id, e);
			
			if (connection != null) {
				try {
					connection.rollback();
					
					logger.info("Rollback eseguito con successo per l'azienda con ID {}", id);
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback per l'azienda con ID {}", id, ex);
				}
			}
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura dello statement per l'azienda con ID {}", id, e);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione per l'azienda con ID {}", id, e);
				}
			}
		}
	}
	
	
	protected Azienda getAziendaUser(int idGestore) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		Azienda azienda = null;
		
		String query = """
				SELECT *
				FROM azienda
				WHERE id_user = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idGestore);
			
			logger.info("Esecuzione della query per ottenere l'azienda con ID gestore: {}", idGestore);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				boolean found = false;
				
				while (resultSet.next()) {
					found = true;
					
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					azienda = new Azienda((int) row.get("id"), (String) row.get("nome"), idGestore);
				}
				
				if (! found) {
					logger.info("Nessuna azienda trovata per il gestore con ID {}", idGestore);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante la ricerca dell'azienda per il gestore con ID {}", idGestore, e);
			
			return null;
		}
		
		return azienda;
	}
	
	
}
