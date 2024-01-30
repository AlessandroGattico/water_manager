package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pissir.watermanager.model.user.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DaoUtils {
	
	private final Logger logger = LogManager.getLogger(DaoUser.class.getName());
	private final Logger loggerSql = LogManager.getLogger("sql");
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoUtils() {
	}
	
	
	public HashSet<String> getRaccolti() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<String> raccolti = new HashSet<>();
		
		String query = """
				SELECT *
				FROM raccolto;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			loggerSql.debug("Executing sql " + query);
			
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
				raccolti.add((String) map.get("nome"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return raccolti;
	}
	
	
	public void addRaccolto(String string) {
		String queryInsert = """
				INSERT INTO raccolto (nome)
				VALUES (?);
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(queryInsert)) {
			loggerSql.debug("Executing sql " + queryInsert);
			loggerSql.debug("Parameters: ");
			
			statement.setString(1, string);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public void deleteRaccolto(String nome) {
		String query = """
				DELETE FROM raccolto
				WHERE nome = ? ;
				""";
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 UUID = " + nome);
			
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public HashSet<String> getEsigenze() {
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
			
			loggerSql.debug("Executing sql " + query);
			
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
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return esigenze;
	}
	
	
	public void addEsigenza(String esigenza) {
		String query = """
				INSERT INTO esigenza (nome)
				VALUES (?);
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 nome = " + esigenza);
			
			statement.setString(1, esigenza);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public void deleteEsigenza(String nome) {
		String query = """
				DELETE FROM esigenza
				WHERE nome = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 UUID = " + nome);
			
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public HashSet<String> getIrrigazioni() {
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
			
			loggerSql.debug("Executing sql " + query);
			
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
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return irrigazioni;
	}
	
	
	public void addIrrigazione(String nome) {
		String queryInsert = """
				INSERT INTO irrigazione (nome)
				VALUES (?);
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(queryInsert)) {
			loggerSql.debug("Executing sql " + queryInsert);
			loggerSql.debug("Parameters: ");
			
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		
	}
	
	
	public void deleteIrrigazione(String nome) {
		String query = """
				DELETE FROM irrigazione
				WHERE nome = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 nome = " + nome);
			
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
	}
	
	
	public HashSet<String> getSensorTypes() {
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
			
			loggerSql.debug("Executing sql " + query);
			
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
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return types;
	}
	
	
	public void addSensorType(String nome) {
		String queryInsert = """
				INSERT INTO sensor_type (type)
				VALUES (?);
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(queryInsert)) {
			loggerSql.debug("Executing sql " + queryInsert);
			loggerSql.debug("Parameters: ");
			
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public void deleteSensorType(String nome) {
		String query = """
				DELETE FROM sensor_type
				WHERE nome = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 nome = " + nome);
			
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
	}
	
	
	public int countGestori(UserRole userRole) {
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
			
			pstmt.setString(1, userRole.toString());
			
			if (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	public int countRaccolti() {
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
	
	
	public int countEsigenze() {
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
	
	
	public int countIrrigazioni() {
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
	
	
	public int countSensorTypes() {
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
