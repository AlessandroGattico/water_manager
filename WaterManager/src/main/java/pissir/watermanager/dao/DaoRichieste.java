package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.RichiestaIdrica;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoRichieste {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	private final String archive =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/ARCHIVE";
	private static final Logger logger = LogManager.getLogger(DaoRichieste.class.getName());
	private static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	protected DaoRichieste() {
	}
	
	
	protected RichiestaIdrica getRichiestaId(int idRichiesta) {
		RichiestaIdrica richiestaIdrica = null;
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idRichiesta);
			
			logger.info("Esecuzione della query per ottenere la richiesta idrica con ID: {}", idRichiesta);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					richiestaIdrica = new RichiestaIdrica(
							resultSet.getInt("id"),
							resultSet.getDouble("quantita"),
							resultSet.getInt("id_coltivazione"),
							resultSet.getInt("id_bacino"),
							resultSet.getString("date"),
							resultSet.getString("nome_azienda")
					);
					
					logger.debug("Trovata richiesta idrica: {}", richiestaIdrica.getId());
				} else {
					logger.info("Nessuna richiesta idrica trovata con ID: {}", idRichiesta);
				}
				
				return richiestaIdrica;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della richiesta idrica con ID: {}", idRichiesta, e);
			
			return richiestaIdrica;
		}
	}
	
	
	protected HashSet<RichiestaIdrica> getRichiesteColtivazione(int idColtivazione) {
		HashSet<RichiestaIdrica> richieste = new HashSet<>();
		RichiestaIdrica richiestaIdrica = null;
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id_coltivazione = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idColtivazione);
			
			logger.info("Esecuzione della query per ottenere le richieste della coltivazione con ID: {}",
					idColtivazione);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					richiestaIdrica = new RichiestaIdrica(
							resultSet.getInt("id"),
							resultSet.getDouble("quantita"),
							resultSet.getInt("id_coltivazione"),
							resultSet.getInt("id_bacino"),
							resultSet.getString("date"),
							resultSet.getString("nome_azienda")
					);
					
					richieste.add(richiestaIdrica);
				}
				
				if (richieste.isEmpty()) {
					logger.info("Nessuna misura trovata per il sensore con ID: {}", idColtivazione);
				} else {
					logger.debug("Richieste idriche trovate per la coltivazione con ID {}: {}", idColtivazione,
							richieste.size());
				}
				return richieste;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle richieste idriche per la coltivazione con ID {}",
					idColtivazione, e);
			
			return richieste;
		}
	}
	
	
	protected HashSet<RichiestaIdrica> getRichiesteBacino(int idBacino) {
		HashSet<RichiestaIdrica> richieste = new HashSet<>();
		RichiestaIdrica richiestaIdrica = null;
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id_bacino = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idBacino);
			
			logger.info("Esecuzione della query per ottenere le richieste del bacino con ID: {}", idBacino);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					richiestaIdrica = new RichiestaIdrica(
							resultSet.getInt("id"),
							resultSet.getDouble("quantita"),
							resultSet.getInt("id_coltivazione"),
							resultSet.getInt("id_bacino"),
							resultSet.getString("date"),
							resultSet.getString("nome_azienda")
					);
					
					richieste.add(richiestaIdrica);
				}
				
				if (richieste.isEmpty()) {
					logger.info("Nessuna misura trovata per il sensore con ID: {}", idBacino);
				} else {
					logger.debug("Richieste idriche trovate per la coltivazione con ID {}: {}", idBacino,
							richieste.size());
				}
				
				return richieste;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle richieste idriche per il bacino con ID {}", idBacino, e);
			
			return null;
		}
	}
	
	
	protected HashSet<RichiestaIdrica> getRichiesteAzienda(String idAzienda) {
		HashSet<RichiestaIdrica> richieste = new HashSet<>();
		RichiestaIdrica richiestaIdrica = null;
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE nome_azienda = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, idAzienda);
			
			logger.info("Esecuzione della query per ottenere le richieste dell'azienda con nome: {}", idAzienda);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					richiestaIdrica = new RichiestaIdrica(
							resultSet.getInt("id"),
							resultSet.getDouble("quantita"),
							resultSet.getInt("id_coltivazione"),
							resultSet.getInt("id_bacino"),
							resultSet.getString("date"),
							resultSet.getString("nome_azienda")
					);
					
					richieste.add(richiestaIdrica);
				}
				
				if (richieste.isEmpty()) {
					logger.info("Nessuna misura trovata per il sensore con ID: {}", idAzienda);
				} else {
					logger.debug("Richieste idriche trovate per la coltivazione con ID {}: {}", idAzienda,
							richieste.size());
				}
				
				return richieste;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle richieste idriche per l'azienda '{}'", idAzienda, e);
			
			return null;
		}
	}
	
	
	protected int addRichiesta(RichiestaIdrica richiesta) {
		int id = 0;
		String query = """
				INSERT INTO richiesta (quantita, id_coltivazione, id_bacino, date, nome_azienda)
				VALUES (?, ?, ?, ?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setDouble(1, richiesta.getQuantita());
				statement.setInt(2, richiesta.getIdColtivazione());
				statement.setInt(3, richiesta.getIdBacino());
				statement.setString(4, richiesta.getDate());
				statement.setString(5, richiesta.getNomeAzienda());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
					}
				}
				
				connection.commit();
				
				logger.info("Richiesta idrica aggiunta con successo: {}", id);
			}
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante il rollback", ex);
				}
			}
			
			logger.error("Errore durante l'aggiunta della richiesta idrica", e);
			
			throw new RuntimeException("Errore durante l'aggiunta della richiesta idrica", e);
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
	
	
	protected void deleteRichiesta(int idRichiesta) {
		String query = """
				DELETE FROM richiesta
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idRichiesta);
				
				int affectedRows = statement.executeUpdate();
				
				if (affectedRows > 0) {
					logger.debug("Misura con ID {} eliminata correttamente", idRichiesta);
				} else {
					logger.info("Nessuna misura trovata con ID {}", idRichiesta);
				}
				
				connection.commit();
			}
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione della coltivazione con ID: {}", idRichiesta, e);
			
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
	
	
	public LinkedList<RichiestaIdrica> getWaiting(int idAzienda) {
		LinkedList<RichiestaIdrica> richieste = new LinkedList<>();
		RichiestaIdrica richiestaIdrica = null;
		
		String query = """
				SELECT *
				FROM waiting_resource_azienda
				WHERE id_azienda = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idAzienda);
			
			logger.info("Esecuzione della query per ottenere le richieste della coltivazione con ID: {}",
					idAzienda);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					richiestaIdrica = new RichiestaIdrica(
							resultSet.getInt("id"),
							resultSet.getDouble("quantita"),
							resultSet.getInt("id_coltivazione"),
							resultSet.getInt("id_bacino"),
							resultSet.getString("date"),
							resultSet.getString("nome_azienda")
					);
					
					richieste.add(richiestaIdrica);
				}
				
				if (richieste.isEmpty()) {
					logger.info("Nessuna misura trovata per il sensore con ID: {}", idAzienda);
				} else {
					logger.debug("Richieste idriche trovate per la coltivazione con ID {}: {}", idAzienda,
							richieste.size());
				}
				return richieste;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle richieste idriche per la coltivazione con ID {}",
					idAzienda, e);
			
			return richieste;
		}
	}
	
	
	protected int addWaiting(int idAzienda, int idRichiesta) {
		int id = 0;
		String query = """
				INSERT INTO waiting_resource_azienda (id_azienda, id_richiesta)
				VALUES (?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setInt(1, idAzienda);
				statement.setInt(2, idRichiesta);
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
					}
				}
				
				connection.commit();
				
				logger.info("Richiesta idrica aggiunta con successo: {}", id);
			}
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante il rollback", ex);
				}
			}
			
			logger.error("Errore durante l'aggiunta della richiesta idrica", e);
			
			throw new RuntimeException("Errore durante l'aggiunta della richiesta idrica", e);
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
	
	
	protected void deleteWaiting(int idAzienda) {
		Connection connection = null;
		String query = """
				DELETE FROM waiting_resource_azienda
				WHERE id_azienda = ? ;
				""";
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idAzienda);
				
				logger.info("Esecuzione della query per eliminare la risorsa idrica aziendale con ID: {}", idAzienda);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.info("Risorsa idrica aziendale eliminata con successo");
				} else {
					logger.info("Nessuna risorsa idrica aziendale trovata con ID: {}", idAzienda);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback della cancellazione della risorsa idrica aziendale", ex);
			}
			
			logger.error("Errore durante la cancellazione della risorsa idrica aziendale", e);
			
			throw new RuntimeException("Errore durante la cancellazione della risorsa idrica aziendale", e);
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
	
	
	public void clearRichieste() {
		int totalMonths = 12 + LocalDateTime.now().getMonthValue() - 1;
		
		LocalDateTime result = LocalDateTime.now()
				.minusMonths(totalMonths)
				.with(TemporalAdjusters.firstDayOfMonth());
		
		String retention = result.format(formatterData);
		
		Archive archive = new Archive(this.url, this.archive, "richiesta", retention);
		
		archive.export();
	}
	
	
	public LinkedList<RichiestaIdrica> getWaitingData(int idAzienda, String ieriString) {
		LinkedList<RichiestaIdrica> richieste = new LinkedList<>();
		RichiestaIdrica richiestaIdrica = null;
		
		String query = """
				SELECT *
				FROM waiting_resource_azienda
				WHERE id_azienda = ? AND date(insert_time) = date(?);
				""";
		
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idAzienda);
			statement.setString(2, ieriString);
			
			logger.info("Esecuzione della query per ottenere le richieste in waiting dell'azienda con ID: {} e " +
							"relative al giorno: {}",
					idAzienda, ieriString);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					richiestaIdrica = new RichiestaIdrica(
							resultSet.getInt("id"),
							resultSet.getDouble("quantita"),
							resultSet.getInt("id_coltivazione"),
							resultSet.getInt("id_bacino"),
							resultSet.getString("date"),
							resultSet.getString("nome_azienda")
					);
					
					richieste.add(richiestaIdrica);
				}
				
				if (richieste.isEmpty()) {
					logger.info("Nessuna misura trovata per il sensore con ID: {}", idAzienda);
				} else {
					logger.debug("Richieste idriche trovate per la coltivazione con ID {}: {}", idAzienda,
							richieste.size());
				}
				return richieste;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle richieste idriche per la coltivazione con ID {}",
					idAzienda, e);
			
			return richieste;
		}
	}
	
	
	public void deleteWaitingData(int idAzienda, String ieriString) {
		Connection connection = null;
		String query = """
				DELETE FROM waiting_resource_azienda
				WHERE id_azienda = ? AND date(insert_time) <= date(?);
				""";
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idAzienda);
				
				logger.info("Esecuzione della query per eliminare la risorsa idrica aziendale con ID: {} e data: {}",
						idAzienda, ieriString);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.info("Risorsa idrica aziendale eliminata con successo");
				} else {
					logger.info("Nessuna risorsa idrica aziendale trovata con ID: {} e data: {}", idAzienda,
							ieriString);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback della cancellazione della risorsa idrica aziendale", ex);
			}
			
			logger.error("Errore durante la cancellazione della risorsa idrica aziendale", e);
			
			throw new RuntimeException("Errore durante la cancellazione della risorsa idrica aziendale", e);
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
	
}
