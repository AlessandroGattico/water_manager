package pissir.watermanager.dao;

import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Campagna;
import pissir.watermanager.model.utils.cambio.CambioString;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoCampagna {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoCampagna() {
	
	}
	
	
	public Campagna getCampagnaId(int id) {
		Campagna campagna = null;
		
		String query = """
				SELECT *
				FROM campagna
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				campagna = new Campagna(resultSet.getInt("id"), resultSet.getString("nome"),
						resultSet.getInt("id_azienda"));
				
			}
			
		} catch (SQLException e) {
			return campagna;
		}
		
		
		return campagna;
	}
	
	
	public HashSet<Campagna> getCampagnaAzienda(int idAzienda) {
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
					Campagna campagna =
							new Campagna((int) map.get("id"), (String) map.get("nome"), (int) map.get("id_azienda"));
					
					campagne.add(campagna);
				}
			}
		} catch (SQLException e) {
			return campagne;
		}
		
		return campagne;
	}
	
	
	public int addCampagna(Campagna campagna) {
		int id = 0;
		
		String query = """
				INSERT INTO campagna (nome, id_azienda)
				VALUES (?, ?);
				SELECT  last_insert_rowid() AS newId;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, campagna.getNome());
			statement.setInt(2, campagna.getIdAzienda());
			
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
	
	
	public void deleteCampagna(int id) {
		String query = """
				DELETE FROM campagna
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
	
	
	public Boolean cambiaNome(CambioString cambio) {
		String query = "UPDATE approvazione SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, cambio.getNewString());
			statement.setInt(2, cambio.getId());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		
		return true;
	}
	
	
	public HashSet<Campagna> getCampagne() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Campagna> campagne = new HashSet<>();
		
		String query = """
				SELECT *
				FROM campagna;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
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
			return campagne;
		}
		
		return campagne;
	}
	
	
	public boolean existsCampagnaAzienda(int id, String campagna) {
		String sql = """
				SELECT COUNT(*)
				FROM campagna
				WHERE nome = ?
				AND id_azienda = ?;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, campagna);
			statement.setInt(2, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			return false;
		}
		
		return false;
	}
	
}
