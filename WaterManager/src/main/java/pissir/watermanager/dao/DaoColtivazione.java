package pissir.watermanager.dao;

import pissir.watermanager.model.item.Coltivazione;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoColtivazione {
	
	private final Logger logger = LogManager.getLogger(DaoColtivazione.class.getName());
	private final Logger loggerSql = LogManager.getLogger("sql");
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/resources/DATABASEWATER";
	
	
	public DaoColtivazione () {
	}
	
	
	public Coltivazione getColtivazioneId (int idColtivazione) {
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
			System.out.println(e.getMessage());
		}
		
		return coltivazione;
	}
	
	
	public HashSet<Coltivazione> getColtivazioniCampo (int idCampo) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Coltivazione> coltivazioni = new HashSet<>();
		
		String query = """
				SELECT *
				FROM coltivazione
				WHERE id = ? ;
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
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return coltivazioni;
	}
	
	
	public int addColtivazione (Coltivazione coltivazione) {
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
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return id;
	}
	
	
	public void deleteColtivazione (int idColtivazione) {
		String query = """
				DELETE FROM coltivazione
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idColtivazione);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public HashSet<String> getRaccolti () {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<String> raccolti = new HashSet<>();
		
		String query = """
				SELECT *
				FROM raccolto;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			loggerSql.debug("Executing sql " + query);
			
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
				raccolti.add((String) map.get("nome"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return raccolti;
	}
	
	
	public void addRaccolto (String string) {
		String queryInsert = """
				INSERT INTO raccolto (nome)
				VALUES (?);
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(queryInsert)) {
			loggerSql.debug("Executing sql " + queryInsert);
			loggerSql.debug("Parameters: ");
			
			statement.setString(1, string);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public void deleteRaccolto (String nome) {
		String query = """
				DELETE FROM raccolto
				WHERE nome = ? ;
				""";
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 UUID = " + nome);
			
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public HashSet<String> getEsigenze () {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<String> esigenze = new HashSet<>();
		
		String query = """
				SELECT *
				FROM esigenza;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			loggerSql.debug("Executing sql " + query);
			
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
				esigenze.add((String) map.get("nome"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return esigenze;
	}
	
	
	public void addEsigenza (String esigenza) {
		String query = """
				INSERT INTO esigenza (nome)
				VALUES (?);
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 nome = " + esigenza);
			
			statement.setString(1, esigenza);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public void deleteEsigenza (String nome) {
		String query = """
				DELETE FROM esigenza
				WHERE nome = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 UUID = " + nome);
			
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
	}
	
	
	public HashSet<String> getIrrigazioni () {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<String> irrigazioni = new HashSet<>();
		
		String query = """
				SELECT *
				FROM irrigazione;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			loggerSql.debug("Executing sql " + query);
			
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
				irrigazioni.add((String) map.get("nome"));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return irrigazioni;
	}
	
	
	public void addIrrigazione (String nome) {
		String queryInsert = """
				INSERT INTO irrigazione (nome)
				VALUES (?);
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(queryInsert)) {
			loggerSql.debug("Executing sql " + queryInsert);
			loggerSql.debug("Parameters: ");
			
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		
	}
	
	
	public void deleteIrrigazione (String nome) {
		String query = """
				DELETE FROM irrigazione
				WHERE nome = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 nome = " + nome);
			
			statement.setString(1, nome);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
	}
	
}
