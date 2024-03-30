package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.controller.ControllerAdmin;
import pissir.watermanager.model.item.Coltivazione;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoColtivazione {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	private static final Logger logger = LogManager.getLogger(DaoColtivazione.class.getName());
	
	
	protected DaoColtivazione() {
	}
	
	
	protected Coltivazione getColtivazioneId(int idColtivazione) {
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
			
			logger.info("Esecuzione della query per ottenere la coltivazione con ID: {}", idColtivazione);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				if (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					coltivazione = new Coltivazione((int) row.get("id"), (String) row.get("raccolto"),
							(String) row.get("irrigazione"), (String) row.get("esigenza"),
							(Double) row.get("temperatura"), (Double) row.get("umidita"), (String) row.get("semina"),
							(int) row.get("id_campo"));
					
					logger.debug("Trovata coltivazione: {}", coltivazione.getRaccolto());
				} else {
					logger.info("Nessuna coltivazione trovata con ID: {}", idColtivazione);
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della coltivazione con ID: {}", idColtivazione, e);
			
			return null;
		}
		
		return coltivazione;
	}
	
	
	protected HashSet<Coltivazione> getColtivazioniCampo(int idCampo) {
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
			
			logger.info("Esecuzione della query per ottenere le coltivazioni del campo con ID: {}", idCampo);
			
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
				
				if (! list.isEmpty()) {
					for (HashMap<String, Object> hash : list) {
						Coltivazione coltivazione =
								new Coltivazione((int) hash.get("id"), (String) hash.get("raccolto"),
										(String) hash.get("irrigazione"), (String) hash.get("esigenza"),
										(Double) hash.get("temperatura"), (Double) hash.get("umidita"),
										(String) hash.get("semina"),
										(int) hash.get("id_campo"));
						
						coltivazioni.add(coltivazione);
						logger.debug("Trovata coltivazione: {}", coltivazione.getRaccolto());
					}
					logger.debug("Trovate {} coltivazioni per il campo con ID: {}", coltivazioni.size(), idCampo);
				} else {
					logger.info("Nessuna coltivazione trovata per il campo con ID: {}", idCampo);
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle coltivazioni per il campo con ID: {}", idCampo, e);
			
			return null;
		}
		
		return coltivazioni;
	}
	
	
	protected int addColtivazione(Coltivazione coltivazione) {
		int id = 0;
		String queryInsert = """
				INSERT INTO coltivazione (raccolto, esigenza, irrigazione, temperatura, umidita, semina, id_campo)
				VALUES (?, ?, ?, ?, ?, ?, ?);
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(queryInsert,
					Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, coltivazione.getRaccolto());
				statement.setString(2, coltivazione.getEsigenza());
				statement.setString(3, coltivazione.getIrrigazione());
				statement.setDouble(4, coltivazione.getTemperatura());
				statement.setDouble(5, coltivazione.getUmidita());
				statement.setString(6, coltivazione.getSemina());
				statement.setInt(7, coltivazione.getIdCampo());
				
				logger.info("Inserimento della coltivazione per il campo ID: {}", coltivazione.getIdCampo());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
						
						logger.debug("Coltivazione aggiunta con successo. ID assegnato: {}", id);
					}
				}
			}
			
			connection.commit();
		} catch (Exception e) {
			logger.error("Errore durante l'inserimento della coltivazione", e);
			
			try {
				if (connection != null) {
					connection.rollback();
					
					logger.info("Eseguito rollback a seguito di un errore");
				}
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback dell'inserimento della coltivazione", ex);
			}
			
			throw new RuntimeException("Errore durante l'inserimento della coltivazione", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore durante la chiusura della connessione al database", e);
				}
			}
		}
		
		return id;
	}
	
	
	protected void deleteColtivazione(int idColtivazione) {
		String query = """
				DELETE FROM coltivazione
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idColtivazione);
				
				logger.info("Tentativo di eliminare la coltivazione con ID: {}", idColtivazione);
				
				int affectedRows = statement.executeUpdate();
				
				if (affectedRows > 0) {
					logger.debug("Eliminazione della coltivazione con ID: {} completata", idColtivazione);
				} else {
					logger.info("Nessuna coltivazione trovata con ID: {}", idColtivazione);
				}
				
				connection.commit();
			}
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione della coltivazione con ID: {}", idColtivazione, e);
			
			try {
				if (connection != null) {
					connection.rollback();
					logger.info("Eseguito rollback a seguito di un errore");
				}
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback dell'eliminazione della coltivazione", ex);
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore durante la chiusura della connessione al database", e);
				}
			}
		}
	}
	
}
