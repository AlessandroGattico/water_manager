package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Attivazione;
import pissir.watermanager.model.utils.cambio.CambioBool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoAttivazioni {
	
	public static final Logger logger = LogManager.getLogger(DaoAttivazioni.class.getName());
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoAttivazioni() {
	}
	
	
	public Attivazione getAttivazioneId(int id) {
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
					attivazione = new Attivazione(resultSet.getInt("id"), resultSet.getString("data"),
							resultSet.getBoolean("stato"), resultSet.getInt("id_attuatore"));
					
				} else {
					logger.warn("Nessuna attivazione trovata con ID {}", id);
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'attivazione con ID {}", id, e);
		}
		
		return attivazione;
	}
	
	
	public HashSet<Attivazione> getAttivazioniAttuatore(int id) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Attivazione> attivazioni = new HashSet<>();
		
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
					Attivazione attivazione =
							new Attivazione((int) map.get("id"), (String) map.get("time"), (Boolean) map.get("current"),
									(Integer) map.get("id_attuatore"));
					
					attivazioni.add(attivazione);
				}
				
				logger.debug("Numero di attivazioni trovate: {}", attivazioni.size());
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle attivazioni per l'attuatore con ID {}", id, e);
			
			return null;
		}
		
		return attivazioni;
	}
	
	
	public int addAttivazione(Attivazione attivazione) {
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
	
	
	public void deleteAttivazione(int idAttivazione) {
		String query = """
				DELETE FROM attivazione
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idAttivazione);
				
				logger.info("Eliminazione dell'attivazione con ID {}", idAttivazione);
				
				statement.executeUpdate();
				
				connection.commit();
			} catch (SQLException e) {
				logger.error("Errore durante l'eliminazione dell'attivazione, eseguo rollback", e);
				
				if (connection != null) {
					connection.rollback();
				}
				
				throw e;
			}
		} catch (SQLException e) {
			logger.error("Errore di connessione durante l'eliminazione dell'attivazione", e);
			
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
	}
	
	
	public Boolean cambiaAttivazione(CambioBool cambio) {
		String query = "UPDATE attivazione SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setBoolean(1, cambio.isNewBool());
				statement.setInt(2, cambio.getId());
				
				logger.info("Aggiornamento dell'attivazione con ID {}", cambio.getId());
				statement.executeUpdate();
				
				connection.commit();
				
				return true;
			} catch (SQLException e) {
				logger.error("Errore durante l'aggiornamento dell'attivazione, eseguo rollback", e);
				
				if (connection != null) {
					connection.rollback();
				}
				
				return false;
			}
		} catch (SQLException e) {
			logger.error("Errore di connessione durante l'aggiornamento dell'attivazione", e);
			
			return false;
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
