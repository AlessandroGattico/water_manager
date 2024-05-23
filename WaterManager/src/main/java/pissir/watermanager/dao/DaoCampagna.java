package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Campagna;

import java.sql.*;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoCampagna {
	
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/DATABASEWATER";
	
	//private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
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
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					campagna = new Campagna(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getInt("id_azienda")
					);
					
					logger.debug("Trovata campagna: {}", campagna.getNome());
				} else {
					logger.info("Nessuna campagna trovata con ID: {}", id);
				}
				
				return campagna;
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della campagna con ID: {}", id, e);
			
			return campagna;
		}
	}
	
	
	protected HashSet<Campagna> getCampagnaAzienda(int idAzienda) {
		HashSet<Campagna> campagne = new HashSet<>();
		Campagna campagna = null;
		
		String query = """
				SELECT *
				FROM campagna
				WHERE id_azienda = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idAzienda);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					campagna = new Campagna(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getInt("id_azienda")
					);
					
					campagne.add(campagna);
				}
				
				if (campagne.isEmpty()) {
					logger.info("Nessuna campagna trovata per l'azienda con ID: {}", idAzienda);
				} else {
					logger.info("Trovate {} campagne per l'azienda con ID: {}", campagne.size(), idAzienda);
				}
				
				return campagne;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle campagne per l'azienda con ID: {}", idAzienda, e);
			
			return campagne;
		}
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
						
						logger.info("Campagna aggiunta con ID {}", id);
					}
				}
			}
			
			connection.commit();
		} catch (Exception e) {
			logger.error("Errore durante l'aggiunta della campagna", e);
			
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
				statement.setInt(1, id);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.info("Campagna {} eliminata con successo.", id);
				} else {
					logger.info("Nessuna campagna trovata con ID: {}.", id);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione della campagna con ID: {}", id, e);
			
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
	
	
	protected HashSet<Campagna> getCampagne() {
		HashSet<Campagna> campagne = new HashSet<>();
		Campagna campagna = null;
		
		String query = """
				SELECT *
				FROM campagna;
				""";
		
		logger.info("Estrazione di tutte le campagne");
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					campagna = new Campagna(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getInt("id_azienda")
					);
					
					campagne.add(campagna);
				}
				
				if (campagne.isEmpty()) {
					logger.info("Nessuna campagna trovata ");
				} else {
					logger.info("Trovate {} campagne ", campagne.size());
				}
				
				return campagne;
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle campagne", e);
			
			return campagne;
		}
	}
	
	
	protected boolean existsCampagnaAzienda(int id, String campagna) {
		String sql = """
				SELECT COUNT(*)
				FROM campagna
				WHERE nome = ?
				AND id_azienda = ?;
				""";
		
		logger.info("Controllo dell nome della campagna {} nell'azienda {}", campagna, id);
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, campagna);
			statement.setInt(2, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				boolean exists = resultSet.getInt(1) > 0;
				
				logger.info("La campagna '{}' {} nell'azienda con ID {}", campagna, exists ? "esiste" : "non esiste",
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
