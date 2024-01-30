package pissir.watermanager.dao;

import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Misura;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoMisura {
	
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoMisura() {
	}
	
	
	public Misura getMisuraId(int idMisura) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		Misura misura = null;
		
		String query = """
				SELECT *
				FROM misura
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idMisura);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					misura = new Misura((int) row.get("id"), (Double) row.get("value"), (String) row.get("time"),
							(int) row.get("id_sensore"));
				}
			}
			
		} catch (SQLException e) {
			
			return null;
		}
		
		return misura;
	}
	
	
	public HashSet<Misura> getMisureSensore(int idSensore) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Misura> misure = new HashSet<>();
		
		String query = """
				SELECT *
				FROM misura
				WHERE id_sensore = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idSensore);
			
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
				
				for (HashMap<String, Object> hash : list) {
					Misura misura =
							new Misura((int) hash.get("id"), (Double) hash.get("value"), (String) hash.get("time"),
									(int) hash.get("id_sensore"));
					
					misure.add(misura);
				}
			}
		} catch (SQLException e) {
			
			return null;
		}
		
		return misure;
	}
	
	
	public void addMisura(Misura misura) {
		String query = """
				INSERT INTO misura (value, time, id_sensore)
				VALUES (?, ?, ?);
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setDouble(1, misura.getValue());
			statement.setString(2, misura.getTime());
			statement.setInt(3, misura.getIdSensore());
			
			statement.executeUpdate();
		} catch (SQLException e) {
		}
	}
	
	
	public void deleteMisura(int idMisura) {
		String query = """
				DELETE FROM misura
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idMisura);
			
			statement.executeUpdate();
		} catch (SQLException e) {
		}
		
	}
	
}
