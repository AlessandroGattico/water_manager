package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.cambio.CambioInt;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.Attuatore;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoAttuatore {
	
	private final Logger logger = LogManager.getLogger(DaoAzienda.class.getName());
	private final Logger loggerSql = LogManager.getLogger("sql");
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/resources/DATABASEWATER";
	
	
	public DaoAttuatore() {
	
	}
	
	
	public Attuatore getAttuatoreId(int uuidAttuatore) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		Attuatore attuatore = null;
		
		String query = """
				SELECT *
				FROM attuatore
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, uuidAttuatore);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					attuatore = new Attuatore((int) row.get("id"), (String) row.get("nome"), (int) row.get("id_campo"));
				}
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		
		return attuatore;
	}
	
	
	public HashSet<Attuatore> getAttuatoriCampo(long uuidCampo) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Attuatore> attuatori = new HashSet<>();
		
		String query = """
				SELECT *
				FROM attuatore
				WHERE id_campo = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, uuidCampo);
			
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
					Attuatore attuatore =
							new Attuatore((int) map.get("id"), (String) map.get("nome"), (int) map.get("id_campo"));
					
					attuatori.add(attuatore);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return attuatori;
	}
	
	
	public int addAttuatore(Attuatore attuatore) {
		int id = 0;
		
		String query = """
				INSERT INTO attuatore (nome, id_campo)
				VALUES (?, ?);
				SELECT  last_insert_rowid() AS newId;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, attuatore.getNome());
			statement.setInt(2, attuatore.getIdCampo());
			
			statement.executeUpdate();
			
			try (ResultSet resultSet = statement.getGeneratedKeys();) {
				if (resultSet.next()) {
					id = resultSet.getInt(1);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return id;
		}
		return id;
	}
	
	
	public void deleteAttuatore(int uuidAttuatore) {
		String query = """
				DELETE FROM attuatore
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, uuidAttuatore);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
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
	
	
	public Boolean cambiaCampo(CambioInt cambio) {
		String query = "UPDATE approvazione SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id = " + cambio.getProperty());
			
			statement.setInt(1, cambio.getNewInt());
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
