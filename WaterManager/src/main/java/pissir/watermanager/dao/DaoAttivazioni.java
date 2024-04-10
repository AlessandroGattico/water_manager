package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Attivazione;

import java.sql.*;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoAttivazioni {
	
	private static final Logger logger = LogManager.getLogger(DaoAttivazioni.class.getName());
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	protected DaoAttivazioni() {
	}
	
	
	protected Attivazione getAttivazioneId(int id) {
		Attivazione attivazione = null;
		String query = """
				SELECT *
				FROM attivazione
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			
			logger.info("Ricerca attivazione con ID {}", id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					attivazione = new Attivazione(
							resultSet.getInt("id"),
							resultSet.getString("data"),
							resultSet.getBoolean("stato"),
							resultSet.getInt("id_attuatore")
					);
					
					logger.info("Attivazione trovata con ID {}", id);
				} else {
					logger.info("Nessuna attivazione trovata con ID {}", id);
				}
				
				return attivazione;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'attivazione con ID {}", id, e);
			
			return attivazione;
		}
	}
	
	
	protected HashSet<Attivazione> getAttivazioniAttuatore(int id) {
		HashSet<Attivazione> attivazioni = new HashSet<>();
		Attivazione attivazione = null;
		
		String query = """
				SELECT *
				FROM attivazione
				WHERE id_attuatore = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			
			logger.info("Recupero attivazioni per attuatore con ID {}", id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					boolean state = resultSet.getInt("current") != 0;
					
					attivazione = new Attivazione(
							resultSet.getInt("id"),
							resultSet.getString("time"),
							state,
							resultSet.getInt("id_attuatore")
					);
					
					attivazioni.add(attivazione);
				}
				
				if (attivazioni.isEmpty()) {
					logger.info("Nessuna attivazione trovata per l'attuatore con ID: {}", id);
				} else {
					logger.debug("Numero di attivazioni trovate: {}", attivazioni.size());
				}
				
				return attivazioni;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle attivazioni per l'attuatore con ID {}", id, e);
			
			return attivazioni;
		}
	}
	
	
	protected int addAttivazione(Attivazione attivazione) {
		int id = 0;
		String query = """
				INSERT INTO attivazione (current, time, id_attuatore)
				VALUES (?, ?, ?);
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setBoolean(1, attivazione.isCurrent());
				statement.setString(2, attivazione.getTime());
				statement.setLong(3, attivazione.getIdAttuatore());
				
				logger.info("Aggiunta attivazione per l'attuatore ID {}", attivazione.getIdAttuatore());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
					}
				}
				
				connection.commit();
			}
		} catch (SQLException e) {
			logger.error("Errore di connessione durante l'aggiunta dell'attivazione", e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
			
			throw new RuntimeException("Errore di connessione", e);
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
	
	
	protected void deleteAttivazione(int idAttivazione) {
		String query = """
				DELETE FROM attivazione
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Tentativo di eliminazione del raccolto: {}", idAttivazione);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idAttivazione);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Raccolto '{}' eliminato con successo.", idAttivazione);
				} else {
					logger.info("Nessun raccolto trovato con nome '{}'.", idAttivazione);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione del raccolto '{}'", idAttivazione, e);
			
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
	
	
	
	
}
