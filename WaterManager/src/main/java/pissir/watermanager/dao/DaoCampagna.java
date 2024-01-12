package pissir.watermanager.dao;

import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.Campagna;
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
public class DaoCampagna {
	
	private final Logger logger = LogManager.getLogger(DaoCampagna.class.getName());
	private final Logger loggerSql = LogManager.getLogger("sql");
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/resources/DATABASEWATER";
	
	
	public DaoCampagna () {
	
	}
	
	
	public Campagna getCampagnaId (long uuidCampagna) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		Campagna campagna = null;
		
		String query = """
				SELECT *
				FROM campagna
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, uuidCampagna);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					campagna = new Campagna((int) row.get("id"), (String) row.get("nome"), (int) row.get("id_azienda"));
				}
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		
		return campagna;
	}
	
	
	public HashSet<Campagna> getCampagnaAzienda (long uuidAzienda) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Campagna> campagne = new HashSet<>();
		
		String query = """
				SELECT *
				FROM campagna
				WHERE id_azienda = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, uuidAzienda);
			
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
					Campagna campagna =
							new Campagna((int) map.get("id"), (String) map.get("nome"), (int) map.get("id_azienda"));
					
					campagne.add(campagna);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return campagne;
	}
	
	
	public int addCampagna (Campagna campagna) {
		int id = 0;
		
		String query = """
				INSERT INTO campagna (nome, id_azienda)
				VALUES (?, ?);
				SELECT  last_insert_rowid() AS newId;
				""";
		
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(2, campagna.getNome());
			statement.setLong(3, campagna.getIdAzienda());
			
			statement.executeUpdate();
			
			try (ResultSet resultSet = statement.getGeneratedKeys();) {
				if (resultSet.next()) {
					id = resultSet.getInt(1);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return id;
		}
		
		return id;
	}
	
	
	public void deleteCampagna (int uuidCampagna) {
		String query = """
				DELETE FROM campagna
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, uuidCampagna);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
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
}
