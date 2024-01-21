package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.RisorsaIdrica;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoRisorseBacino {
	
	private final Logger logger = LogManager.getLogger(DaoRichieste.class.getName());
	private final Logger loggerSql = LogManager.getLogger("sql");
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoRisorseBacino() {
	}
	
	
	public RisorsaIdrica getRisorsaBacinoId(int idRisorsa) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		RisorsaIdrica risorsaIdrica = null;
		
		String query = """
				SELECT *
				FROM risorsa_bacino
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idRisorsa);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					risorsaIdrica = new RisorsaIdrica((int) row.get("id"), (String) row.get(
							"time"), (Double) row.get("disponibilita"), (Double) row.get("consumo"),
							(int) row.get("id_azienda"));
				}
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return risorsaIdrica;
	}
	
	
	public HashSet<RisorsaIdrica> getStoricoRisorseBacino(int idBacino) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RisorsaIdrica> risorse = new HashSet<>();
		
		String query = """
				SELECT *
				FROM risorsa_bacino
				WHERE id_bacino = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, idBacino);
			
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
					RisorsaIdrica risorsaIdrica = new RisorsaIdrica((int) map.get("id"), (String) map.get(
							"time"), (Double) map.get("disponibilita"), (Double) map.get("consumo"),
							(int) map.get("id_azienda"));
					
					risorse.add(risorsaIdrica);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			return null;
		}
		
		return risorse;
	}
	
	
	public int addRisorsaBacino(RisorsaIdrica risorsaIdrica) {
		int id = 0;
		
		String query = """
				INSERT INTO risorsa_bacino (consumo, disponibilita, time, id_bacino)
				VALUES (?, ?, ?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDouble(1, risorsaIdrica.getConsumo());
			statement.setDouble(2, risorsaIdrica.getDisponibilita());
			statement.setString(3, risorsaIdrica.getData());
			statement.setInt(4, risorsaIdrica.getIdSource());
			
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
	
	
	public void deleteRisorsaBacino(int id) {
		String query = """
				DELETE FROM risorsa_bacino
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, id);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
	}
	
}
