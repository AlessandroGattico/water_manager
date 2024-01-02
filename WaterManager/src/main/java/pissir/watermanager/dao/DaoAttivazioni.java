package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.cambio.CambioBool;
import pissir.watermanager.model.item.Attivazione;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoAttivazioni {
	
	private static final Logger logger = LogManager.getLogger(DaoAttivazioni.class.getName());
	private static final Logger loggerSql = LogManager.getLogger("sql");
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/resources/DATABASEWATER";
	
	
	public DaoAttivazioni () {
	}
	
	
	public Attivazione getAttivazioneId (int uuidAttivazione) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		Attivazione attivazione = null;
		
		String query = """
				SELECT *
				FROM attivazione
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, uuidAttivazione);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					attivazione = new Attivazione((int) row.get("id"), (String) row.get("data"),
							(boolean) row.get("stato"), (Integer) row.get("id_attuatore"));
				}
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		
		return attivazione;
	}
	
	
	public HashSet<Attivazione> getAttivazioniAttuatore (int uuidAttuatore) {
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
			
			statement.setLong(1, uuidAttuatore);
			
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
							new Attivazione((int) map.get("id"), (String) map.get("data"), (boolean) map.get("stato"),
									(Integer) map.get("id_attuatore"));
					
					attivazioni.add(attivazione);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return attivazioni;
	}
	
	
	public int addAttivazione (Attivazione attivazione) {
		int id = 0;
		String query = """
				INSERT INTO attivazione (current, time, id_attuatore)
				VALUES (?, ?, ?);
				SELECT  last_insert_rowid() AS newId;
				""";
		
		try (
				Connection connection = DriverManager.getConnection(this.url);
				PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setBoolean(1, attivazione.isCurrent());
			statement.setString(2, attivazione.getTime());
			statement.setLong(3, attivazione.getIdAttuatore());
			
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
	
	
	public void deleteAttivazione (int attivazione) {
		String query = """
				DELETE FROM attivazione
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, attivazione);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public Boolean cambiaAttivazione (CambioBool cambio) {
		String query = "UPDATE attivazione SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id = " + cambio.getProperty());
			
			statement.setBoolean(1, cambio.isNewBool());
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
