package pissir.watermanager.dao;

import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Attivazione;
import pissir.watermanager.model.utils.cambio.CambioBool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoAttivazioni {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoAttivazioni() {
	}
	
	
	public Attivazione getAttivazioneId(int uuidAttivazione) {
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
				while (resultSet.next()) {
					attivazione = new Attivazione(resultSet.getInt("id"), resultSet.getString("data"),
							resultSet.getInt("stato"), resultSet.getInt("id_attuatore"));
				}
			}
			
		} catch (SQLException e) {
			return null;
		}
		
		return attivazione;
	}
	
	
	public HashSet<Attivazione> getAttivazioniAttuatore(int uuidAttuatore) {
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
							new Attivazione((int) map.get("id"), (String) map.get("time"), (Integer) map.get("current"),
									(Integer) map.get("id_attuatore"));
					
					attivazioni.add(attivazione);
				}
			}
		} catch (SQLException e) {
			return null;
		}
		
		return attivazioni;
	}
	
	
	public int addAttivazione(Attivazione attivazione) {
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
			return id;
		}
		
		return id;
	}
	
	
	public void deleteAttivazione(int attivazione) {
		String query = """
				DELETE FROM attivazione
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, attivazione);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
	}
	
	
	public Boolean cambiaAttivazione(CambioBool cambio) {
		String query = "UPDATE attivazione SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setBoolean(1, cambio.isNewBool());
			statement.setInt(2, cambio.getId());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		
		return true;
	}
	
}
