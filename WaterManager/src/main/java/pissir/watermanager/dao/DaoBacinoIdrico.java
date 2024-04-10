package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.BacinoIdrico;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoBacinoIdrico {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	private static final Logger logger = LogManager.getLogger(DaoBacinoIdrico.class.getName());
	
	
	protected DaoBacinoIdrico() {
	}
	
	
	protected BacinoIdrico getBacinoId(int id) {
		ResultSetMetaData resultSetMetaData;
		BacinoIdrico bacinoIdrico = null;
		
		String query = """
				SELECT *
				FROM bacino
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					bacinoIdrico = new BacinoIdrico(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getInt("id_user")
					);
					
					logger.info("Bacino idrico trovato: {}", bacinoIdrico.getNome());
				} else {
					logger.info("Nessun bacino idrico trovato con ID {}", id);
				}
				
				return bacinoIdrico;
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero del bacino con ID {}", id, e);
			
			return bacinoIdrico;
		}
	}
	
	
	protected HashSet<BacinoIdrico> getBacini() {
		HashSet<BacinoIdrico> bacini = new HashSet<>();
		BacinoIdrico bacinoIdrico = null;
		
		String query = """
				SELECT *
				FROM bacino;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			while (resultSet.next()) {
				bacinoIdrico = new BacinoIdrico(
						resultSet.getInt("id"),
						resultSet.getString("nome"),
						resultSet.getInt("id_user")
				);
				
				bacini.add(bacinoIdrico);
			}
			
			if (bacini.isEmpty()) {
				logger.info("Nessun bacino trovato");
				
				return bacini;
			} else {
				logger.info("Trovati {} bacini", bacini.size());
			}
			
			return bacini;
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei bacini", e);
			
			return bacini;
		}
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
						logger.info("Bacino aggiunto con ID {}", id);
					}
				}
			}
			
			connection.commit();
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
	
	
	protected void deleteBacino(int bacino) {
		String query = """
				DELETE FROM bacino
				WHERE id = ? ;
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Tentativo di eliminazione del raccolto: {}", bacino);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, bacino);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Raccolto '{}' eliminato con successo.", bacino);
				} else {
					logger.info("Nessun raccolto trovato con nome '{}'.", bacino);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione del raccolto '{}'", bacino, e);
			
			if (connection != null) {
				try {
					connection.rollback();
					
					logger.info("Rollback eseguito a seguito di un errore.");
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
			
			throw new RuntimeException("Errore durante l'eliminazione del raccolto", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
					
					logger.info("Connessione chiusa.");
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
	}
	
	
	protected BacinoIdrico getBacinoUser(int idGestore) {
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
				if (resultSet.next()) {
					bacinoIdrico = new BacinoIdrico(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							idGestore
					);
					
					logger.info("Bacino idrico trovato: {}", bacinoIdrico.getNome());
				} else {
					logger.info("Nessun bacino trovato per il gestore con ID {}", idGestore);
				}
				
				return bacinoIdrico;
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante la ricerca del bacino per il gestore con ID {}", idGestore, e);
			
			return bacinoIdrico;
		}
	}
	
}
