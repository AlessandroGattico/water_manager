package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.utils.cambio.CambioBool;
import pissir.watermanager.model.item.Approvazione;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoApprovazione {
	
	private static final Logger logger = LogManager.getLogger(DaoApprovazione.class.getName());
	private static final Logger loggerSql = LogManager.getLogger("sql");
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoApprovazione() {
	}
	
	
	public Approvazione getApprovazioneIdRichiesta(int idRichiesta) {
		Approvazione approvazione = null;
		
		String query = """
				SELECT *
				FROM approvazione
				WHERE id_richiesta = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id_richiesta = " + idRichiesta);
			
			statement.setInt(1, idRichiesta);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				//resultSetMetaData = resultSet.getMetaData();
				//columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					/*row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					 */
					
					approvazione = new Approvazione(resultSet.getInt("id"), resultSet.getInt("id_richiesta"),
							resultSet.getInt("id_gestore"), resultSet.getBoolean("approvato"),
							resultSet.getString("date"));
				}
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return approvazione;
	}
	
	
	public HashSet<Approvazione> getApprovazioniGestore(int idGestore) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Approvazione> approvazioni = new HashSet<>();
		
		String query = """
				SELECT *
				FROM approvazione
				WHERE id_gestore = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id_gestore = " + idGestore);
			
			statement.setInt(1, idGestore);
			
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
					Approvazione approvazione = new Approvazione((int) map.get("id"), (int) map.get("idRichiesta"),
							(int) map.get("idGestore"), (boolean) map.get("approvato"), (String) map.get("date"));
					
					approvazioni.add(approvazione);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return approvazioni;
	}
	
	
	public int addApprovazione(Approvazione approvazione) {
		int id = 0;
		String query = """
				INSERT INTO approvazione (id_richiesta, id_gestore, approvato, date)
				VALUES (?, ?, ?, ?);
				SELECT  last_insert_rowid() AS newId;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id_richiesta = " + approvazione.getIdRichiesta());
			loggerSql.debug("?2 id_gestore = " + approvazione.getIdGestore());
			loggerSql.debug("?3 approvato = " + approvazione.isApprovato());
			loggerSql.debug("?4 date = " + approvazione.getDate());
			
			statement.setInt(1, approvazione.getIdRichiesta());
			statement.setInt(2, approvazione.getIdGestore());
			statement.setBoolean(3, approvazione.isApprovato());
			statement.setString(4, approvazione.getDate());
			
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
	
	
	public void deleteApprovazione(int idApprovazione) {
		String query = """
				DELETE FROM approvazione
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id = " + idApprovazione);
			
			statement.setLong(1, idApprovazione);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public Boolean cambiaApprovazione(CambioBool cambio) {
		String query = "UPDATE approvazione SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		
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
