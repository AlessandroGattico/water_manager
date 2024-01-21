package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.cambio.CambioInt;
import pissir.watermanager.model.cambio.CambioString;
import pissir.watermanager.model.item.Campo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoCampo {
	
	private final Logger logger = LogManager.getLogger(DaoCampo.class.getName());
	private final Logger loggerSql = LogManager.getLogger("sql");
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoCampo() {
	}
	
	
	public Campo getCampoId(int idCampo) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		Campo campo = null;
		
		String query = """
				SELECT *
				FROM campo
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, idCampo);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					campo = new Campo((int) row.get("id"), (String) row.get("nome"),
							(Double) row.get("dimensione"), (int) row.get("id_campagna"));
				}
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return campo;
	}
	
	
	public HashSet<Campo> getCampiCampagna(int idCampagna) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Campo> campi = new HashSet<>();
		
		String query = """
				SELECT *
				FROM campo
				WHERE id_campagna = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idCampagna);
			
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
					Campo campo = new Campo((int) map.get("id"), (String) map.get("nome"),
							(Double) map.get("dimensione"), (int) map.get("id_campagna"));
					
					campi.add(campo);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return campi;
	}
	
	
	public int addCampo(Campo campo) {
		int id = 0;
		
		String query = """
				INSERT INTO campo (nome, id_campagna, dimensione)
				VALUES (?, ?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, campo.getNome());
			statement.setInt(2, campo.getIdCampagna());
			statement.setDouble(3, campo.getDimensione());
			
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
	
	
	public void deleteCampo(int idCampo) {
		String query = """
				DELETE FROM campo
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idCampo);
			
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
	
	
	public Boolean cambiaCampagna(CambioInt cambio) {
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
