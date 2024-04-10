package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Attuatore;

import java.sql.*;
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
	
	
	protected Attuatore getAttuatoreId(int idAttuatore) {
		Attuatore attuatore = null;
		String query = """
				SELECT *
				FROM attuatore
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, idAttuatore);
			
			logger.info("Recupero dell'attuatore con ID {}", idAttuatore);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					attuatore = new Attuatore(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getInt("id_campo")
					);
					
					logger.info("Attuatore trovato: {}", attuatore.getNome());
				} else {
					logger.info("Nessun attuatore trovato con ID {}", idAttuatore);
				}
				
				return attuatore;
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'attuatore con ID {}", idAttuatore, e);
			
			return attuatore;
		}
	}
	
	
	protected HashSet<Attuatore> getAttuatoriCampo(long idCampo) {
		HashSet<Attuatore> attuatori = new HashSet<>();
		Attuatore attuatore = null;
		
		String query = """
				SELECT *
				FROM attuatore
				WHERE id_campo = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, idCampo);
			
			logger.info("Recupero degli attuatori per il campo con ID {}", idCampo);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					attuatore = new Attuatore(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getInt("id_campo")
					);
					
					attuatori.add(attuatore);
				}
				
				if (attuatori.isEmpty()) {
					logger.info("Nessuna attuatore trovato per il campo con ID: {}", idCampo);
				} else {
					logger.info("Numero di attuatori trovati: {}", attuatori.size());
				}
				
				return attuatori;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero degli attuatori per il campo con ID {}", idCampo, e);
			
			return attuatori;
		}
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
