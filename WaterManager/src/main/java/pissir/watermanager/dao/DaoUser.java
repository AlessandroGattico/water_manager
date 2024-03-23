package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.controller.ControllerAdmin;
import pissir.watermanager.model.user.*;
import pissir.watermanager.model.utils.cambio.CambioString;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoUser {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	public static final Logger logger = LogManager.getLogger(ControllerAdmin.class.getName());
	
	
	public DaoUser() {
	}
	
	
	public Admin getAdmin(int id) {
		Admin admin = null;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		
		String query = """
				SELECT *
				FROM users
				WHERE id = ?
				AND role = 'SYSTEMADMIN';
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			
			logger.info("Esecuzione della query per ottenere admin con ID: {}", id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					admin = new Admin((int) row.get("id"), (String) row.get("nome"), (String) row.get("cognome"),
							(String) row.get("username"), (String) row.get("mail"), (String) row.get("password"));
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'admin con ID: {}", id, e);
		}
		
		if (admin == null) {
			logger.info("Nessun admin trovato con ID: {}", id);
		}
		
		return admin;
	}
	
	
	public HashSet<GestoreIdrico> getGestoriIdrici() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<GestoreIdrico> utenti = new HashSet<>();
		
		String query = """
				SELECT *
				FROM users
				WHERE role = 'GESTOREIDRICO';
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			logger.info("Esecuzione della query per ottenere i gestori idrici");
			
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
				GestoreIdrico user =
						new GestoreIdrico((int) map.get("id"), (String) map.get("nome"), (String) map.get("cognome"),
								(String) map.get("username"), (String) map.get("mail"), "");
				
				utenti.add(user);
				
				logger.debug("Aggiunto gestore idrico: {}", user.getUsername());
			}
			
			if (utenti.isEmpty()) {
				logger.info("Nessun gestore idrico trovato");
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei gestori idrici", e);
			return null;
		}
		
		return utenti;
	}
	
	
	public HashSet<GestoreAzienda> getGestoriAzienda() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<GestoreAzienda> utenti = new HashSet<>();
		
		String query = """
				SELECT *
				FROM users
				WHERE role = 'GESTOREAZIENDA';
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			logger.info("Esecuzione della query per ottenere i gestori azienda");
			
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
				GestoreAzienda user =
						new GestoreAzienda((int) map.get("id"), (String) map.get("nome"), (String) map.get("cognome"),
								(String) map.get("username"), (String) map.get("mail"), "");
				
				utenti.add(user);
				logger.debug("Aggiunto gestore azienda: {}", user.getUsername());
			}
			
			if (utenti.isEmpty()) {
				logger.info("Nessun gestore azienda trovato");
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei gestori azienda", e);
			return null;
		}
		
		return utenti;
	}
	
	
	public UserProfile getUser(String username, String password) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		UserProfile user = null;
		
		String query = """
				SELECT *
				FROM users
				WHERE username = ?
				AND password = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, username);
			statement.setString(2, password);
			
			logger.info("Esecuzione della query per ottenere l'utente con username: {}", username);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					user = new UserProfile((int) row.get("id"), (String) row.get("nome"), (String) row.get("cognome"),
							(String) row.get("username"), (String) row.get("mail"), (String) row.get("password"),
							UserRole.valueOf((String) row.get("role")));
					
					logger.debug("Trovato utente: {}", user.getUsername());
				} else {
					logger.info("Nessun utente trovato con username: {}", username);
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'utente con username: {}", username, e);
			
			return null;
		}
		
		return user;
	}
	
	
	public UserProfile getUserByUsername(String username) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		UserProfile user = null;
		
		String query = """
				SELECT *
				FROM users
				WHERE username = ?;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, username);
			
			logger.info("Esecuzione della query per ottenere l'utente con username: {}", username);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (resultSet.next()) {
					row = new HashMap<>(columns);
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					user = new UserProfile((int) row.get("id"), (String) row.get("nome"), (String) row.get("cognome"),
							(String) row.get("username"), (String) row.get("mail"), (String) row.get("password"),
							UserRole.valueOf((String) row.get("role")));
					
					logger.debug("Trovato utente: {}", user.getUsername());
				} else {
					logger.info("Nessun utente trovato con username: {}", username);
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'utente con username: {}", username, e);
			
			return null;
		}
		
		return user;
	}
	
	
	public HashSet<UserProfile> getUtenti() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<UserProfile> utenti = new HashSet<>();
		
		String query = """
				SELECT *
				FROM users
				WHERE role <> 'SYSTEMADMIN';
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			logger.info("Esecuzione della query per ottenere tutti gli utenti escluso SYSTEMADMIN");
			
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
					UserProfile user = new UserProfile(
							(int) map.get("id"),
							(String) map.get("nome"),
							(String) map.get("cognome"),
							(String) map.get("username"),
							(String) map.get("mail"),
							(String) map.get("password"),
							UserRole.valueOf((String) map.get("role"))
					);
					
					utenti.add(user);
				}
				
				logger.debug("Numero di utenti trovati: {}", utenti.size());
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero degli utenti", e);
			
			return null;
		}
		
		return utenti;
	}
	
	
	public int addUser(UserProfile user) {
		int id = 0;
		String query = """
				INSERT INTO users (nome, cognome, username, mail, password, role)
				VALUES (?, ?, ?, ?, ?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, user.getNome());
				statement.setString(2, user.getCognome());
				statement.setString(3, user.getUsername());
				statement.setString(4, user.getMail());
				statement.setString(5, user.getPassword());
				statement.setString(6, user.getRole().toString());
				
				logger.info("Esecuzione della query di inserimento per l'utente con username: {}", user.getUsername());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
						
						logger.debug("Utente inserito con ID: {}", id);
					}
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback dopo un fallimento nell'inserimento dell'utente", ex);
			}
			
			logger.error("Errore durante l'inserimento dell'utente", e);
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
	
	
	public void deleteUser(UserProfile user) {
		String query = """
				DELETE FROM users
				WHERE username = ?
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, user.getUsername());
				
				logger.info("Esecuzione della query di eliminazione per l'utente con username: {}", user.getUsername());
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Utente {} eliminato con successo.", user.getUsername());
				} else {
					logger.info("Nessun utente trovato con username: {}", user.getUsername());
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback dopo un fallimento nell'eliminazione dell'utente", ex);
			}
			
			logger.error("Errore durante l'eliminazione dell'utente", e);
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
	
	
	public GestoreIdrico getGestoreIdrico(int id) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		GestoreIdrico user = null;
		
		String query = """
				SELECT *
				FROM users
				WHERE id = ?;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			
			logger.info("Esecuzione della query per ottenere il gestore idrico con ID: {}", id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					user = new GestoreIdrico((int) row.get("id"), (String) row.get("nome"), (String) row.get("cognome"),
							(String) row.get("username"), (String) row.get("mail"), (String) row.get("password"));
					logger.debug("Trovato gestore idrico: {}", user.getUsername());
				} else {
					logger.info("Nessun gestore idrico trovato con ID: {}", id);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero del gestore idrico con ID: {}", id, e);
			return null;
		}
		
		return user;
	}
	
	
	public GestoreAzienda getGestoreAzienda(int id) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		GestoreAzienda user = null;
		
		String query = """
				SELECT *
				FROM users
				WHERE id = ?;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			
			logger.info("Esecuzione della query per ottenere il gestore azienda con ID: {}", id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					user = new GestoreAzienda((int) row.get("id"), (String) row.get("nome"),
							(String) row.get("cognome"), (String) row.get("username"), (String) row.get("mail"),
							(String) row.get("password"));
					logger.debug("Trovato gestore azienda: {}", user.getUsername());
				} else {
					logger.info("Nessun gestore azienda trovato con ID: {}", id);
				}
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero del gestore azienda con ID: {}", id, e);
			
			return null;
		}
		
		return user;
	}
	
	
	public Boolean cambiaString(CambioString cambio) {
		String query = "UPDATE users SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, cambio.getNewString());
				statement.setInt(2, cambio.getId());
				
				int affectedRows = statement.executeUpdate();
				connection.commit();
				
				if (affectedRows > 0) {
					logger.info("Modifica effettuata con successo per l'utente con ID: {}", cambio.getId());
					
					return true;
				} else {
					logger.info("Nessuna riga modificata per l'utente con ID: {}", cambio.getId());
					
					return false;
				}
			} catch (SQLException e) {
				logger.error("Errore nell'esecuzione della query. Esecuzione del rollback.", e);
				
				connection.rollback();
				return false;
			}
		} catch (SQLException e) {
			logger.error("Errore di connessione al database", e);
			
			return false;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione al database", e);
				}
			}
		}
	}
	
	
	public boolean existsUsername(String username) {
		String sql = """
				SELECT COUNT(*)
				FROM users
				WHERE username = ?;""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, username);
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				
				logger.debug("Risultato della verifica esistenza username '{}': {}", username, count);
				
				return count > 0;
			} else {
				logger.info("Nessun risultato trovato per username '{}'", username);
				
				return false;
			}
		} catch (SQLException e) {
			logger.error("Errore durante la verifica dell'esistenza dell'username '{}'", username, e);
			
			return false;
		}
	}
	
	
	public boolean existsMail(String email) {
		String sql = """
				SELECT COUNT(*)
				FROM users
				WHERE mail = ?""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, email);
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
				int count = rs.getInt(1);
				
				logger.debug("Risultato della verifica esistenza email '{}': {}", email, count);
				
				return count > 0;
			} else {
				logger.info("Nessun risultato trovato per email '{}'", email);
				
				return false;
			}
		} catch (SQLException e) {
			logger.error("Errore durante la verifica dell'esistenza dell'email '{}'", email, e);
			
			return false;
		}
	}
	
}
