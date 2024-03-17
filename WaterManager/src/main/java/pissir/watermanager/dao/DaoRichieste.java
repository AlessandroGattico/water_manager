package pissir.watermanager.dao;

import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.RichiestaIdrica;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoRichieste {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	public DaoRichieste() {
	}
	
	
	public RichiestaIdrica getRichiestaId(int idRichiesta) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		RichiestaIdrica richiestaIdrica = null;
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idRichiesta);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					richiestaIdrica = new RichiestaIdrica((int) row.get("id"), (Double) row.get("quantita"),
							(int) row.get("id_coltivazione"), (int) row.get("id_bacino"), (String) row.get("date"),
							(String) row.get("nome_azienda"));
				}
			}
		} catch (SQLException e) {
			return null;
		}
		
		return richiestaIdrica;
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteColtivazione(int idColtivazione) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RichiestaIdrica> richieste = new HashSet<>();
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id_coltivazione = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idColtivazione);
			
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
					RichiestaIdrica richiestaIdrica =
							new RichiestaIdrica((int) map.get("id"), (Double) map.get("quantita"),
									(int) map.get("id_coltivazione"), (int) map.get("id_bacino"),
									(String) map.get("date"), (String) map.get("nome_azienda"));
					
					richieste.add(richiestaIdrica);
				}
			}
		} catch (SQLException e) {
			return null;
		}
		
		return richieste;
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteBacino(int idBacino) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RichiestaIdrica> richieste = new HashSet<>();
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id_bacino = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idBacino);
			
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
					RichiestaIdrica richiestaIdrica =
							new RichiestaIdrica((int) map.get("id"), (Double) map.get("quantita"),
									(int) map.get("id_coltivazione"), (int) map.get("id_bacino"),
									(String) map.get("date"), (String) map.get("nome_azienda"));
					
					richieste.add(richiestaIdrica);
				}
			}
		} catch (SQLException e) {
			return null;
		}
		
		return richieste;
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteAzienda(String idAzienda) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RichiestaIdrica> richieste = new HashSet<>();
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE nome_azienda = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, idAzienda);
			
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
					RichiestaIdrica RichiestaIdrica =
							new RichiestaIdrica((int) map.get("id"), (Double) map.get("quantita"),
									(int) map.get("id_coltivazione"), (int) map.get("id_bacino"),
									(String) map.get("date"), (String) map.get("nome_azienda"));
					
					richieste.add(RichiestaIdrica);
				}
			}
		} catch (SQLException e) {
			return null;
		}
		
		return richieste;
	}
	
	
	public int addRichiesta(RichiestaIdrica richiesta) {
		int id = 0;
		String query = """
				INSERT INTO richiesta (quantita, id_coltivazione, id_bacino, date, nome_azienda)
				VALUES (?, ?, ?, ?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDouble(1, richiesta.getQuantita());
			statement.setInt(2, richiesta.getIdColtivazione());
			statement.setInt(3, richiesta.getIdBacino());
			statement.setString(4, richiesta.getDate());
			statement.setString(5, richiesta.getNomeAzienda());
			
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
	
	
	public void deleteRichiesta(int idRichiesta) {
		String query = """
				DELETE FROM richiesta
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idRichiesta);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			return;
		}
	}
	
}
