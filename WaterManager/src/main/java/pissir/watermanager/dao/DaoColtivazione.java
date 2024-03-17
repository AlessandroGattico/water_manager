package pissir.watermanager.dao;

import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Coltivazione;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoColtivazione {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoColtivazione() {
	}
	
	
	public Coltivazione getColtivazioneId(int idColtivazione) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		Coltivazione coltivazione = null;
		
		String query = """
				SELECT *
				FROM coltivazione
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idColtivazione);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					coltivazione = new Coltivazione((int) row.get("id"), (String) row.get("raccolto"),
							(String) row.get("irrigazione"), (String) row.get("esigenza"),
							(Double) row.get("temperatura"), (Double) row.get("umidita"), (String) row.get("semina"),
							(int) row.get("id_campo"));
				}
			}
		} catch (SQLException e) {
			return null;
		}
		
		return coltivazione;
	}
	
	
	public HashSet<Coltivazione> getColtivazioniCampo(int idCampo) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Coltivazione> coltivazioni = new HashSet<>();
		
		String query = """
				SELECT *
				FROM coltivazione
				WHERE id_campo = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, idCampo);
			
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
					Coltivazione coltivazione = new Coltivazione((int) hash.get("id"), (String) hash.get("raccolto"),
							(String) hash.get("irrigazione"), (String) hash.get("esigenza"),
							(Double) hash.get("temperatura"), (Double) hash.get("umidita"), (String) hash.get("semina"),
							(int) hash.get("id_campo"));
					
					coltivazioni.add(coltivazione);
				}
			}
		} catch (SQLException e) {
			return null;
		}
		
		return coltivazioni;
	}
	
	
	public int addColtivazione(Coltivazione coltivazione) {
		int id = 0;
		String queryInsert = """
				INSERT INTO coltivazione (raccolto, esigenza, irrigazione, temperatura, umidita, semina, id_campo)
				VALUES (?, ?, ?, ?, ?, ?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(queryInsert)) {
			statement.setString(1, coltivazione.getRaccolto());
			statement.setString(2, coltivazione.getEsigenza());
			statement.setString(3, coltivazione.getIrrigazione());
			statement.setDouble(4, coltivazione.getTemperatura());
			statement.setDouble(5, coltivazione.getUmidita());
			statement.setString(6, coltivazione.getSemina());
			statement.setInt(7, coltivazione.getIdCampo());
			
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
	
	
	public void deleteColtivazione(int idColtivazione) {
		String query = """
				DELETE FROM coltivazione
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idColtivazione);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
	}
	
}
