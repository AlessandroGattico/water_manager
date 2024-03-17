package pissir.watermanager.dao;

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
public class DaoRisorseAzienda {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoRisorseAzienda() {
	}
	
	
	public RisorsaIdrica getRisorsaAziendaId(int idRisorsa) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		RisorsaIdrica risorsaIdrica = null;
		
		String query = """
				SELECT *
				FROM risorsa_azienda
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
			return null;
		}
		
		return risorsaIdrica;
	}
	
	
	public HashSet<RisorsaIdrica> getStoricoRisorseAzienda(int idAzienda) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RisorsaIdrica> risorse = new HashSet<>();
		
		String query = """
				SELECT *
				FROM risorsa_azienda
				WHERE id_azienda = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idAzienda);
			
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
			return null;
		}
		
		return risorse;
	}
	
	
	public int addRisorsaAzienda(RisorsaIdrica risorsaIdrica) {
		int id = 0;
		
		String query = """
				INSERT INTO risorsa_azienda (consumo, disponibilita, time, id_azienda)
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
			return id;
		}
		
		return id;
	}
	
	
	public void deleteRisorsaAzienda(int id) {
		String query = """
				DELETE FROM risorsa_azienda
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
		
	}
	
	
	public RisorsaIdrica ultimaRisorsa(int idAzienda) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		RisorsaIdrica risorsa = null;
		
		String query = """
				SELECT * FROM risorsa_azienda
				WHERE id_azienda = ?
				ORDER BY time
				DESC LIMIT 1;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idAzienda);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					risorsa = new RisorsaIdrica((int) row.get("id"), (String) row.get(
							"time"), (Double) row.get("disponibilita"), (Double) row.get("consumo"),
							(int) row.get("id_azienda"));
				}
			}
			
		} catch (SQLException e) {
			return null;
		}
		
		return risorsa;
	}
	
}
