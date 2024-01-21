package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.BacinoIdrico;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoBacinoIdrico {
	
	private final Logger logger = LogManager.getLogger(DaoBacinoIdrico.class.getName());
	private final Logger loggerSql = LogManager.getLogger("sql");
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoBacinoIdrico() {
	}
	
	
	public BacinoIdrico getBacinoId(int bacino) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		BacinoIdrico bacinoIdrico = null;
		
		String query = """
				SELECT *
				FROM bacino
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, bacino);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					bacinoIdrico =
							new BacinoIdrico((int) row.get("int"), (String) row.get("nome"), (int) row.get("id_user"));
				}
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return bacinoIdrico;
	}
	
	
	public HashSet<BacinoIdrico> getBacini() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<BacinoIdrico> bacini = new HashSet<>();
		
		String query = """
				SELECT *
				FROM bacino;
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
				BacinoIdrico bacinoIdrico =
						new BacinoIdrico((int) map.get("int"), (String) map.get("nome"), (int) map.get("id_user"));
				
				bacini.add(bacinoIdrico);
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return bacini;
	}
	
	
	public int addBacino(BacinoIdrico bacinoIdrico) {
		int id = 0;
		
		String query = """
				INSERT INTO bacino (nome, id_user)
				VALUES (?, ?);
				SELECT  last_insert_rowid() AS newId;
				""";
		
		try (
				Connection connection = DriverManager.getConnection(this.url);
				PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, bacinoIdrico.getNome());
			statement.setInt(2, bacinoIdrico.getIdGestore());
			
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
	
	
	public void deleteBacino(BacinoIdrico bacino) {
		String query = """
				DELETE FROM bacino
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, bacino.getId());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
	}
	
	
	public BacinoIdrico getBacinoUser(int idGestore) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		BacinoIdrico bacinoIdrico = null;
		
		String query = """
				SELECT *
				FROM bacino
				WHERE id_user = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idGestore);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					bacinoIdrico = new BacinoIdrico((int) row.get("id"), (String) row.get("nome"), idGestore);
				}
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return bacinoIdrico;
	}
	
	
	public Boolean cambiaNome(CambioString cambio) {
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
