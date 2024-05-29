package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.user.*;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoUser {
	
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/DATABASEWATER";
	private static final Logger logger = LogManager.getLogger(DaoUser.class.getName());
	
	
	protected DaoUser() {
	}
	
	
	protected Admin getAdmin(int id) {
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
					
					admin = new Admin(
							(int) row.get("id"),
							(String) row.get("nome"),
							(String) row.get("cognome"),
							(String) row.get("username"),
							(String) row.get("mail"),
							(String) row.get("password")
					);
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
	
	
	protected HashSet<GestoreIdrico> getGestoriIdrici() {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<GestoreIdrico> utenti = new HashSet<>();
		GestoreIdrico user = null;
		
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
			
			while (resultSet.next()) {
				row = new HashMap<>(columns);
				
				for (int i = 1; i <= columns; ++ i) {
					row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
				}
				
				int attivo = (int) row.get("enabled");
				boolean enabled;
				
				if (attivo == 0) {
					enabled = false;
				} else {
					enabled = true;
				}
				
				user = new GestoreIdrico(
						(int) row.get("id"),
						(String) row.get("nome"),
						(String) row.get("cognome"),
						(String) row.get("username"),
						(String) row.get("mail"),
						"",
						enabled
				);
				
				utenti.add(user);
			}
			
			if (utenti.isEmpty()) {
				logger.info("Nessun gestore idrico trovato");
			} else {
				logger.info("Trovati {} gestori idrici", utenti.size());
			}
			
			return utenti;
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei gestori idrici", e);
			
			return utenti;
		}
	}
	
	
	protected HashSet<GestoreAzienda> getGestoriAzienda() {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<GestoreAzienda> utenti = new HashSet<>();
		GestoreAzienda user = null;
		
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
			
			while (resultSet.next()) {
				row = new HashMap<>(columns);
				
				for (int i = 1; i <= columns; ++ i) {
					row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
				}
				
				int attivo = (int) row.get("enabled");
				boolean enabled;
				
				if (attivo == 0) {
					enabled = false;
				} else {
					enabled = true;
				}
				
				user = new GestoreAzienda(
						(int) row.get("id"),
						(String) row.get("nome"),
						(String) row.get("cognome"),
						(String) row.get("username"),
						(String) row.get("mail"),
						"",
						enabled
				);
				
				utenti.add(user);
			}
			
			if (utenti.isEmpty()) {
				logger.info("Nessun gestore azienda trovato");
			} else {
				logger.info("Trovati {} gestori azienda", utenti.size());
			}
			
			return utenti;
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei gestori azienda", e);
			
			return utenti;
		}
	}
	
	
	protected UserProfile getUser(int idUser) {
		UserProfile user = null;
		
		String query = """
				SELECT *
				FROM users
				WHERE id = ?;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idUser);
			
			logger.info("Esecuzione della query per ottenere l'utente con ID: {}", idUser);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int attivo = resultSet.getInt("enabled");
					boolean enabled = attivo != 0;
					
					user = new UserProfile(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getString("cognome"),
							resultSet.getString("username"),
							resultSet.getString("mail"),
							"",
							UserRole.valueOf(resultSet.getString("role")),
							enabled
					);
					
					logger.debug("Trovato utente: {}", user.getUsername());
				} else {
					logger.info("Nessun utente trovato con ID: {}", idUser);
				}
				
				return user;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'utente con ID: {}", idUser, e);
			
			return user;
		}
	}
	
	
	protected UserProfile getUserByUsername(String username) {
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
				if (resultSet.next()) {
					int attivo = resultSet.getInt("enabled");
					boolean enabled = attivo != 0;
					
					user = new UserProfile(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getString("cognome"),
							resultSet.getString("username"),
							resultSet.getString("mail"),
							resultSet.getString("password"),
							UserRole.valueOf(resultSet.getString("role")),
							enabled
					);
					
					logger.debug("Trovato utente: {}", user.getUsername());
				} else {
					logger.info("Nessun utente trovato con username: {}", user.getUsername());
				}
				
				return user;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'utente con username: {}", username, e);
			
			return user;
		}
	}
	
	
	protected HashSet<UserProfile> getUtenti() {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<UserProfile> utenti = new HashSet<>();
		UserProfile user = null;
		
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
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					int attivo = resultSet.getInt("enabled");
					boolean enabled = attivo != 0;
					
					user = new UserProfile(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getString("cognome"),
							resultSet.getString("username"),
							resultSet.getString("mail"),
							"",
							UserRole.valueOf(resultSet.getString("role")),
							enabled
					);
					
					utenti.add(user);
				}
				
				if (utenti.isEmpty()) {
					logger.info("Nessun utente trovato");
				} else {
					logger.debug("Numero di utenti trovati: {}", utenti.size());
				}
				
				return utenti;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero degli utenti", e);
			
			return utenti;
		}
	}
	
	
	protected int addUser(UserProfile user) {
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
	
	
	protected void deleteUser(UserProfile user) {
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
	
	
	protected GestoreIdrico getGestoreIdrico(int id) {
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
				if (resultSet.next()) {
					int attivo = resultSet.getInt("enabled");
					boolean enabled = attivo != 0;
					
					user = new GestoreIdrico(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getString("cognome"),
							resultSet.getString("username"),
							resultSet.getString("mail"),
							resultSet.getString("password"),
							enabled
					);
					
					logger.debug("Trovato utente: {}", user.getUsername());
				} else {
					logger.info("Nessun gestore idrico trovato con ID: {}", id);
				}
				
				return user;
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero del gestore idrico con ID: {}", id, e);
			
			return user;
		}
	}
	
	
	protected GestoreAzienda getGestoreAzienda(int id) {
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
				if (resultSet.next()) {
					int attivo = resultSet.getInt("enabled");
					boolean enabled = attivo != 0;
					
					user = new GestoreAzienda(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getString("cognome"),
							resultSet.getString("username"),
							resultSet.getString("mail"),
							resultSet.getString("password"),
							enabled
					);
					
					logger.debug("Trovato gestore azienda: {}", user.getUsername());
				} else {
					logger.info("Nessun gestore azienda trovato con ID: {}", id);
				}
				
				return user;
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero del gestore azienda con ID: {}", id, e);
			
			return user;
		}
	}
	
	
	protected boolean existsUsername(String username) {
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
	
	
	protected boolean existsMail(String email) {
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
	
	
	protected boolean updateUserStatus(int id, boolean isActive) {
		String sql = "UPDATE users SET enabled = ? WHERE id = ?;";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setBoolean(1, isActive);
				statement.setInt(2, id);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					connection.commit();
					
					logger.debug("Stato dell'utente con ID {} aggiornato a {}", id, isActive);
					
					return true;
				} else {
					logger.info("Nessun utente trovato con ID {}", id);
					
					return false;
				}
			} catch (SQLException e) {
				connection.rollback();
				
				logger.error("Errore durante l'aggiornamento dello stato dell'utente con ID {}", id, e);
				
				return false;
			}
		} catch (SQLException e) {
			logger.error("Errore durante la connessione al database", e);
			
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
