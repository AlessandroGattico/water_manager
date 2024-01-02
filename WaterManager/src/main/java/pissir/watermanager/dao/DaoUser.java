package pissir.watermanager.dao;

import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.user.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoUser {
	
	private final Logger logger = LogManager.getLogger(DaoUser.class.getName());
	private final Logger loggerSql = LogManager.getLogger("sql");
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/resources/DATABASEWATER";
	
	
	public DaoUser () {
	}
	
	
	public Admin getAdmin (String username, String password) {
		Admin admin = (Admin) this.getUser(username, password);
		
		admin.setGestoriAziende(this.getGestoriAzienda());
		admin.setGestoriIdrici(this.getGestoriIdrici());
		
		return admin;
	}
	
	
	private HashSet<GestoreIdrico> getGestoriIdrici () {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<GestoreIdrico> utenti = new HashSet<>();
		
		String query = """
				SELECT *
				FROM user
				WHERE role = 'GESTOREIDRICO';
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
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
				GestoreIdrico user = new GestoreIdrico((int) map.get("id"), (String) map.get("nome"),
						(String) map.get("cognome"), (String) map.get("username"), (String) map.get("mail"),
						(String) map.get("password"));
				
				utenti.add(user);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return utenti;
	}
	
	
	private HashSet<GestoreAzienda> getGestoriAzienda () {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<GestoreAzienda> utenti = new HashSet<>();
		
		String query = """
				SELECT *
				FROM user
				WHERE role = 'GESTOREAZIENDA';
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
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
				GestoreAzienda user = new GestoreAzienda((int) map.get("id"), (String) map.get("nome"),
						(String) map.get("cognome"), (String) map.get("username"), (String) map.get("mail"),
						(String) map.get("password"));
				
				utenti.add(user);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return utenti;
	}
	
	
	public UserProfile getUser (String username, String password) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		UserProfile user = null;
		
		String query = """
				SELECT *
				FROM user
				WHERE username = ?
				AND password = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, username);
			statement.setString(2, password);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					user = new UserProfile((int) row.get("id"), (String) row.get("nome"),
							(String) row.get("cognome"), (String) row.get("username"), (String) row.get("mail"),
							(String) row.get("password"),
							(UserRole) row.get("Role"));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return user;
	}
	
	
	public HashSet<UserProfile> getUtenti () {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<UserProfile> utenti = new HashSet<>();
		
		String query = """
				SELECT *
				FROM user
				WHERE role <> 'SYSTEMADMIN';
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
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
				UserProfile user = new UserProfile((int) map.get("id"), (String) map.get("nome"),
						(String) map.get("cognome"), (String) map.get("username"), (String) map.get("mail"),
						(String) map.get("password"),
						(UserRole) map.get("role"));
				
				utenti.add(user);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return utenti;
	}
	
	
	public int addUser (UserProfile user) {
		int id = 0;
		
		String query = """
				INSERT INTO user (nome, cognome, username, mail, password, role)
				VALUES (?, ?, ?, ?, ?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, user.getNome());
			statement.setString(2, user.getCognome());
			statement.setString(3, user.getUsername());
			statement.setString(4, user.getMail());
			statement.setString(5, user.getPassword());
			statement.setString(6, user.getRole().toString());
			
			statement.executeUpdate();
			
			try (ResultSet resultSet = statement.getGeneratedKeys();) {
				if (resultSet.next()) {
					id = resultSet.getInt(1);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return id;
	}
	
	
	public void deleteUser (UserProfile user) {
		String query = """
				DELETE FROM user
				WHERE username = ?
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, user.getUsername());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public GestoreIdrico getGestoreIdrico (String username, String password) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		GestoreIdrico user = null;
		
		String query = """
				SELECT *
				FROM user
				WHERE username = ?
				AND password = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, username);
			statement.setString(2, password);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					user = new GestoreIdrico((int) row.get("id"), (String) row.get("nome"),
							(String) row.get("cognome"), (String) row.get("username"), (String) row.get("mail"),
							(String) row.get("password"));
				}
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		
		return user;
	}
	
	
	public GestoreAzienda getGestoreAzienda (String username, String password) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		GestoreAzienda user = null;
		
		String query = """
				SELECT *
				FROM user
				WHERE username = ?
				AND password = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, username);
			statement.setString(2, password);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					user = new GestoreAzienda((int) row.get("id"), (String) row.get("nome"),
							(String) row.get("cognome"), (String) row.get("username"), (String) row.get("mail"),
							(String) row.get("password"));
				}
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		
		return user;
	}
	
	
	public Boolean cambiaNome (CambioString cambio) {
		String query = "UPDATE approvazione SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id = " + cambio.getProperty());
			
			statement.setString(1, cambio.getNewString());
			statement.setInt(2, cambio.getId());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return false;
		}
		
		return true;
	}
	
	
	public Boolean cambiaCognome (CambioString cambio) {
		String query = "UPDATE approvazione SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id = " + cambio.getProperty());
			
			statement.setString(1, cambio.getNewString());
			statement.setInt(2, cambio.getId());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return false;
		}
		
		return true;
	}
	
	
	public Boolean cambiaPassword (CambioString cambio) {
		String query = "UPDATE approvazione SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id = " + cambio.getProperty());
			
			statement.setString(1, cambio.getNewString());
			statement.setInt(2, cambio.getId());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return false;
		}
		
		return true;
	}
	
}
