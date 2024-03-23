package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.BacinoIdrico;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoBacinoIdrico {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	public static final Logger logger = LogManager.getLogger(DaoBacinoIdrico.class.getName());
	
	
	public DaoBacinoIdrico() {
	}
	
	
	protected BacinoIdrico getBacinoId(int bacino) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		BacinoIdrico bacinoIdrico = null;
		
		String query = """
				SELECT *
				FROM bacino
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, bacino);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (! resultSet.isBeforeFirst()) {
					logger.info("Nessun bacino trovato con ID {}", bacino);
					
					return null;
				}
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					bacinoIdrico =
							new BacinoIdrico((int) row.get("id"), (String) row.get("nome"), (int) row.get("id_user"));
				}
				
				logger.info("Bacino trovato con ID {}", bacino);
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero del bacino con ID {}", bacino, e);
			
			return null;
		}
		
		return bacinoIdrico;
	}
	
	
	protected HashSet<BacinoIdrico> getBacini() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<BacinoIdrico> bacini = new HashSet<>();
		
		String query = """
				SELECT *
				FROM bacino;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			resultSetMetaData = resultSet.getMetaData();
			columns = resultSetMetaData.getColumnCount();
			list = new ArrayList<>();
			
			if (! resultSet.isBeforeFirst()) {
				logger.info("Nessun bacino trovato");
				
				return bacini;
			}
			
			while (resultSet.next()) {
				row = new HashMap<>(columns);
				
				for (int i = 1; i <= columns; ++ i) {
					row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
				}
				
				list.add(row);
			}
			
			for (HashMap<String, Object> map : list) {
				BacinoIdrico bacinoIdrico =
						new BacinoIdrico((int) map.get("id"), (String) map.get("nome"), (int) map.get("id_user"));
				
				bacini.add(bacinoIdrico);
			}
			
			logger.info("Trovati {} bacini", bacini.size());
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei bacini", e);
			
			return null;
		}
		
		return bacini;
	}
	
	
	protected int addBacino(BacinoIdrico bacinoIdrico) {
		int id = 0;
		Connection connection = null;
		
		String query = """
				INSERT INTO bacino (nome, id_user)
				VALUES (?, ?);
				""";
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, bacinoIdrico.getNome());
				statement.setInt(2, bacinoIdrico.getIdGestore());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys();) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
					}
				}
			}
			connection.commit();
			
			logger.info("Bacino aggiunto con ID {}", id);
		} catch (Exception e) {
			logger.error("Errore durante l'aggiunta del bacino {}.", bacinoIdrico.getNome(), e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
			
			throw new RuntimeException("Errore durante l'aggiunta del bacino", e);
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
	
	
	public void deleteBacino(int bacino) {
		String query = """
				DELETE FROM bacino
				WHERE id = ? ;
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, bacino);
				
				int affectedRows = statement.executeUpdate();
				
				if (affectedRows == 0) {
					logger.warn("Nessun bacino trovato con ID: {}", bacino);
				} else {
					logger.info("Bacino con ID {} eliminato con successo", bacino);
				}
				
				connection.commit();
			} catch (SQLException e) {
				if (connection != null) {
					logger.error("Rollback dell'eliminazione del bacino a causa di un'eccezione", e);
					connection.rollback();
				}
				
				throw e;
			}
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione del bacino con ID: {}", bacino, e);
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
	
	
	public BacinoIdrico getBacinoUser(int idGestore) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		BacinoIdrico bacinoIdrico = null;
		
		String query = """
				SELECT *
				FROM bacino
				WHERE id_user = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idGestore);
			
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
					
					bacinoIdrico = new BacinoIdrico((int) row.get("id"), (String) row.get("nome"), idGestore);
				}
				
				if (! found) {
					logger.info("Nessun bacino trovato per il gestore con ID {}", idGestore);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante la ricerca del bacino per il gestore con ID {}", idGestore, e);
			
			return null;
		}
		
		return bacinoIdrico;
	}
	
}
