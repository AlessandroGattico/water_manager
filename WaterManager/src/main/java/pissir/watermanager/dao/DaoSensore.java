package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Sensore;

import java.sql.*;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoSensore {
	
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/DATABASEWATER";

	//private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	private static final Logger logger = LogManager.getLogger(DaoSensore.class.getName());
	
	
	protected DaoSensore() {
	}
	
	
	protected Sensore getSensoreId(int id) {
		Sensore sensore = null;
		
		String query = """
				SELECT *
				FROM sensore
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					sensore = new Sensore(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getString("type"),
							resultSet.getInt("id_campo")
					);
					
					logger.debug("Trovato sensore: {}", sensore.getNome());
				} else {
					logger.info("Nessun sensore trovato con ID {}", id);
				}
				
				return sensore;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero del sensore con ID {}", id, e);
			
			return sensore;
		}
	}
	
	
	protected HashSet<Sensore> getSensoriCampo(int idCampo) {
		HashSet<Sensore> sensori = new HashSet<>();
		Sensore sensore = null;
		
		String query = """
				SELECT *
				FROM sensore
				WHERE id_campo = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, idCampo);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					sensore = new Sensore(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getString("type"),
							resultSet.getInt("id_campo")
					);
					
					sensori.add(sensore);
				}
				
				if (sensori.isEmpty()) {
					logger.debug("Nessun sensore trovato per il campo con ID: {}", idCampo);
				} else {
					logger.debug("Trovati {} sensori per il campo con ID {}", sensori.size(), idCampo);
				}
				
				return sensori;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei sensori per il campo con ID {}", idCampo, e);
			
			return sensori;
		}
	}
	
	
	protected int addSensore(Sensore sensore) {
		int id = 0;
		String queryInsert = """
				INSERT INTO sensore (nome, type, id_campo)
				VALUES (?, ?, ?);
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(queryInsert,
					Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, sensore.getNome());
				statement.setString(2, sensore.getType());
				statement.setInt(3, sensore.getIdCampo());
				
				logger.info("Inserimento del sensore: {}", sensore.getNome());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
						
						logger.debug("Sensore inserito con ID: {}", id);
					}
				}
				
				connection.commit();
			}
		} catch (SQLException e) {
			logger.error("Errore durante l'inserimento del sensore", e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
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
	
	
	protected void deleteSensore(int idSensore) {
		String query = """
				DELETE FROM sensore
				WHERE id = ?;
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idSensore);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.info("Sensore con ID {} eliminato con successo", idSensore);
				} else {
					logger.info("Nessun sensore trovato con ID {}", idSensore);
				}
				
				connection.commit();
			}
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione del sensore con ID: {}", idSensore, e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
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
	
	
}
