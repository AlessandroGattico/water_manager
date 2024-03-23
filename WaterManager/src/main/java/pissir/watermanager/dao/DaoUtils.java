package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pissir.watermanager.controller.ControllerAdmin;
import pissir.watermanager.model.user.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */


public class DaoUtils {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	public static final Logger logger = LogManager.getLogger(ControllerAdmin.class.getName());
	
	
	public DaoUtils() {
	}
	
	
	protected HashSet<String> getRaccolti() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<String> raccolti = new HashSet<>();
		
		String query = """
				SELECT *
				FROM raccolto;""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			logger.info("Inizio della procedura di recupero dei raccolti dal database");
			
			ResultSet resultSet = statement.executeQuery();
			
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
				String raccolto = (String) map.get("nome");
				raccolti.add(raccolto);
			}
			
			logger.info("Completato il recupero di {} raccolti", raccolti.size());
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei raccolti", e);
			
			return null;
		}
		
		return raccolti;
	}
	
	
	protected void addRaccolto(String string) {
		String queryInsert = """
				INSERT INTO raccolto (nome)
				VALUES (?);""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(queryInsert)) {
				statement.setString(1, string);
				
				logger.info("Inserimento del raccolto '{}' nel database", string);
				
				int rowsAffected = statement.executeUpdate();
				
				connection.commit();
				
				logger.info("Inserimento raccolto completato");
			} catch (SQLException e) {
				logger.error("Errore durante l'inserimento del raccolto: {}", string, e);
				
				if (connection != null) {
					try {
						connection.rollback();
						
						logger.info("Rollback eseguito a seguito di un errore");
					} catch (SQLException ex) {
						logger.error("Errore durante il rollback", ex);
					}
				}
				throw e;
			}
		} catch (SQLException e) {
			logger.error("Errore durante la connessione al database", e);
			
			throw new RuntimeException("Errore durante la connessione al database", e);
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
	
	
	protected void deleteRaccolto(String nome) {
		String query = """
				DELETE FROM raccolto
				WHERE nome = ? ;
				""";
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
	}
	
	
	protected HashSet<String> getEsigenze() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<String> esigenze = new HashSet<>();
		
		String query = """
				SELECT *
				FROM esigenza;
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
				esigenze.add((String) map.get("nome"));
			}
		} catch (SQLException e) {
			return null;
		}
		
		return esigenze;
	}
	
	
	protected void addEsigenza(String esigenza) {
		String query = """
				INSERT INTO esigenza (nome)
				VALUES (?);
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, esigenza);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
	}
	
	
	protected void deleteEsigenza(String nome) {
		String query = """
				DELETE FROM esigenza
				WHERE nome = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
	}
	
	
	protected HashSet<String> getIrrigazioni() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<String> irrigazioni = new HashSet<>();
		
		String query = """
				SELECT *
				FROM irrigazione;
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
				irrigazioni.add((String) map.get("nome"));
			}
		} catch (SQLException e) {
			return null;
		}
		
		return irrigazioni;
	}
	
	
	protected void addIrrigazione(String nome) {
		String queryInsert = """
				INSERT INTO irrigazione (nome)
				VALUES (?);
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(queryInsert)) {
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
	}
	
	
	protected void deleteIrrigazione(String nome) {
		String query = """
				DELETE FROM irrigazione
				WHERE nome = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
		
	}
	
	
	protected HashSet<String> getSensorTypes() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<String> types = new HashSet<>();
		
		String query = """
				SELECT *
				FROM sensor_type;
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
				types.add((String) map.get("type"));
			}
		} catch (SQLException e) {
			return null;
		}
		
		return types;
	}
	
	
	protected void addSensorType(String nome) {
		String queryInsert = """
				INSERT INTO sensor_type (type)
				VALUES (?);
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(queryInsert)) {
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
	}
	
	
	protected void deleteSensorType(String nome) {
		String query = """
				DELETE FROM sensor_type
				WHERE type = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
		
	}
	
	
	protected int countGestori(UserRole userRole) {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM users
				WHERE role = ?;
				""";
		
		try (Connection conn = DriverManager.getConnection(this.url);
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			
			pstmt.setString(1, String.valueOf(userRole));
			
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countRaccolti() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM raccolto;
				""";
		
		try (Connection conn = DriverManager.getConnection(this.url);
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countEsigenze() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM esigenza;
				""";
		
		try (Connection conn = DriverManager.getConnection(this.url);
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countIrrigazioni() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM irrigazione;
				""";
		
		try (Connection conn = DriverManager.getConnection(this.url);
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countSensorTypes() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM sensor_type;
				""";
		
		try (Connection conn = DriverManager.getConnection(this.url);
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	public int countCampi() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM campo;
				""";
		
		try (Connection conn = DriverManager.getConnection(this.url);
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	public int countCampagne() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM campagna;
				""";
		
		try (Connection conn = DriverManager.getConnection(this.url);
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	public int countAziende() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM azienda;
				""";
		
		try (Connection conn = DriverManager.getConnection(this.url);
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	public int countBacini() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM bacino;
				""";
		
		try (Connection conn = DriverManager.getConnection(this.url);
			 PreparedStatement pstmt = conn.prepareStatement(query)) {
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
}
