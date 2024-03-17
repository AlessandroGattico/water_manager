package pissir.watermanager.dao;

import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Azienda;
import pissir.watermanager.model.utils.cambio.CambioString;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoAzienda {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoAzienda() {
	}
	
	
	public Azienda getAziendaId(int id) {
		Azienda azienda = null;
		
		String query = """
				SELECT *
				FROM azienda
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				
				while (resultSet.next()) {
					azienda = new Azienda(resultSet.getInt("id"), resultSet.getString("nome"),
							resultSet.getInt("id_user"));
				}
			}
			
		} catch (SQLException e) {
			return null;
		}
		
		return azienda;
	}
	
	
	public HashSet<Azienda> getAziende() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Azienda> aziende = new HashSet<>();
		
		String query = """
				SELECT *
				FROM azienda;
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
				Azienda azienda = new Azienda((int) map.get("id"), (String) map.get("nome"), (int) map.get("id_user"));
				aziende.add(azienda);
			}
			
		} catch (SQLException e) {
			return null;
		}
		
		return aziende;
	}
	
	
	//aqggiungere id in return
	public int addAzienda(Azienda azienda) {
		int id = 0;
		
		String query = """
				INSERT INTO azienda (nome, id_user)
				VALUES (?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, azienda.getNome());
			statement.setInt(2, azienda.getIdGestore());
			
			statement.executeUpdate();
			
			try (ResultSet resultSet = statement.getGeneratedKeys();) {
				if (resultSet.next()) {
					id = resultSet.getInt(1);
				}
			}
		} catch (Exception e) {
			return id;
		}
		
		return id;
	}
	
	
	public void deleteAzienda(int id) {
		String query = """
				DELETE FROM azienda
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
	}
	
	
	public Azienda getAziendaUser(int idGestore) {
		Azienda azienda = null;
		
		String query = """
				SELECT *
				FROM azienda
				WHERE id_user = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idGestore);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet == null) {
					return null;
				}
				
				while (resultSet.next()) {
					azienda = new Azienda(resultSet.getInt("id"), resultSet.getString("nome"), idGestore);
				}
			}
			
		} catch (SQLException e) {
			return null;
		}
		
		return azienda;
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
	
}
