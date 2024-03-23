package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.controller.ControllerAdmin;
import pissir.watermanager.model.item.Campo;
import pissir.watermanager.model.utils.cambio.CambioInt;
import pissir.watermanager.model.utils.cambio.CambioString;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author alessandrogattico
 */

@Repository
public class DaoCampo {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	public static final Logger logger = LogManager.getLogger(DaoCampo.class.getName());
	
	
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
			
			logger.info("Inizio della query per ottenere il campo con ID: {}", idCampo);
			
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
					
					logger.debug("Trovato campo: {}", campo.getNome());
				}
			}
			
			if (campo == null) {
				logger.info("Nessun campo trovato con ID: {}", idCampo);
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante la ricerca del campo con ID: {}", idCampo, e);
			
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
			
			logger.info("Esecuzione della query per ottenere i campi della campagna con ID: {}", idCampagna);
			
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
							(Double) map.get("dimensione"), idCampagna);
					
					campi.add(campo);
					
					logger.debug("Campo aggiunto: {}", campo.getNome());
				}
				
				if (campi.isEmpty()) {
					logger.info("Nessun campo trovato per la campagna con ID: {}", idCampagna);
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei campi per la campagna con ID: {}", idCampagna, e);
			
			return null;
		}
		
		return campi;
	}
	
	
	public int addCampo(Campo campo) {
		int id = 0;
		String query = """
				INSERT INTO campo (nome, id_campagna, dimensione)
				VALUES (?, ?, ?);
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, campo.getNome());
				statement.setInt(2, campo.getIdCampagna());
				statement.setDouble(3, campo.getDimensione());
				
				logger.info("Inserimento del campo '{}' per la campagna con ID {}", campo.getNome(),
						campo.getIdCampagna());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
					}
				}
				
				connection.commit();
				
				logger.debug("Campo '{}' inserito con successo con ID {}", campo.getNome(), id);
			}
		} catch (SQLException e) {
			logger.error("Errore durante l'inserimento del campo '{}'", campo.getNome(), e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante il rollback", ex);
				}
			}
			
			return id;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore durante la chiusura della connessione", e);
				}
			}
		}
		
		return id;
	}
	
	
	public void deleteCampo(int idCampo) {
		String query = """
				DELETE FROM campo
				WHERE id = ? ;
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idCampo);
				
				logger.info("Tentativo di eliminazione del campo con ID {}", idCampo);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Campo con ID {} eliminato con successo", idCampo);
					
					connection.commit();
				} else {
					logger.info("Nessun campo trovato con ID {}", idCampo);
					
					connection.rollback();
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione del campo con ID {}", idCampo, e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante il rollback", ex);
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore durante la chiusura della connessione", e);
				}
			}
		}
	}
	
	
	public Boolean cambiaNome(CambioString cambio) {
		String query = "UPDATE campo SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, cambio.getNewString());
				statement.setInt(2, cambio.getId());
				
				logger.info("Aggiornamento del nome del campo con ID {}", cambio.getId());
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Nome del campo con ID {} aggiornato con successo", cambio.getId());
					
					connection.commit();
					return true;
				} else {
					logger.info("Nessun campo trovato con ID {}", cambio.getId());
					
					connection.rollback();
					return false;
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante l'aggiornamento del nome del campo con ID {}", cambio.getId(), e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante il rollback", ex);
				}
			}
			
			return false;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore durante la chiusura della connessione", e);
				}
			}
		}
	}
	
	
	public Boolean cambiaCampagna(CambioInt cambio) {
		String query = "UPDATE approvazione SET " + cambio.getProperty() + " = ? WHERE id = ?;";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, cambio.getNewInt());
				statement.setInt(2, cambio.getId());
				
				logger.info("Modifica del valore di '{}' per la campagna con ID {}", cambio.getProperty(),
						cambio.getId());
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Valore '{}' modificato con successo per la campagna con ID {}", cambio.getProperty(),
							cambio.getId());
					
					connection.commit();
					return true;
				} else {
					logger.info("Nessuna modifica apportata alla campagna con ID {}", cambio.getId());
					
					connection.rollback();
					return false;
				}
			}
		} catch (SQLException e) {
			logger.error("Errore durante la modifica della campagna con ID {}", cambio.getId(), e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante il rollback", ex);
				}
			}
			
			return false;
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore durante la chiusura della connessione", e);
				}
			}
		}
	}
	
	
	public HashSet<Campo> getCampi() {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<Campo> campi = new HashSet<>();
		
		String query = """
				SELECT *
				FROM campo;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			logger.info("Recupero tutti i campi");
			
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
					
					logger.debug("Trovato campo: {}", campo.getNome());
				}
				
				logger.debug("Numero di campi recuperati: {}", campi.size());
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei campi", e);
			
			return null;
		}
		
		return campi;
	}
	
	
	public boolean existsCampoCampagna(int id, String campo) {
		String sql = """
				SELECT COUNT(*)
				FROM campo
				WHERE nome = ?
				AND id_campagna = ?;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			
			statement.setString(1, campo);
			statement.setInt(2, id);
			
			logger.info("Verifica esistenza del campo '{}' nella campagna con ID {}", campo, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
				boolean exists = rs.getInt(1) > 0;
				
				logger.debug("Esistenza del campo '{}': {}", campo, exists);
				
				return exists;
			} else {
				logger.debug("Campo '{}' non trovato nella campagna con ID {}", campo, id);
				
				return false;
			}
		} catch (SQLException e) {
			logger.error("Errore durante la verifica dell'esistenza del campo '{}' nella campagna con ID {}", campo, id,
					e);
			
			return false;
		}
	}
	
}
