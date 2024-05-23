package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.RisorsaIdrica;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoRisorseBacino {
	
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/DATABASEWATER";
	private final String archive = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/ARCHIVE";
	
	//private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	//private final String archive = "jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/ARCHIVE";
	private static final Logger logger = LogManager.getLogger(DaoRisorseBacino.class.getName());
	private static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	protected DaoRisorseBacino() {
	}
	
	
	protected RisorsaIdrica getRisorsaBacinoId(int idRisorsa) {
		RisorsaIdrica risorsaIdrica = null;
		
		String query = """
				SELECT *
				FROM risorsa_bacino
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idRisorsa);
			
			logger.info("Esecuzione della query per ottenere la risorsa idrica del bacino con ID: {}", idRisorsa);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					risorsaIdrica = new RisorsaIdrica(
							resultSet.getInt("id"),
							resultSet.getString("time"),
							resultSet.getDouble("disponibilita"),
							resultSet.getDouble("consumo"),
							resultSet.getInt("id_bacino")
					);
					
					logger.debug("Trovata risorsa idrica del bacino: {}", idRisorsa);
				} else {
					logger.info("Nessuna risorsa idrica del bacino trovata con ID: {}", idRisorsa);
				}
				
				return risorsaIdrica;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della risorsa idrica del bacino con ID: {}", idRisorsa, e);
			
			return risorsaIdrica;
		}
	}
	
	
	protected HashSet<RisorsaIdrica> getStoricoRisorseBacino(int idBacino) {
		HashSet<RisorsaIdrica> risorse = new HashSet<>();
		RisorsaIdrica risorsaIdrica = null;
		
		String query = """
				SELECT *
				FROM risorsa_bacino
				WHERE id_bacino = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, idBacino);
			
			logger.info("Esecuzione della query per ottenere lo storico delle risorse idriche del bacino con ID: {}",
					idBacino);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					risorsaIdrica = new RisorsaIdrica(
							resultSet.getInt("id"),
							resultSet.getString("time"),
							resultSet.getDouble("disponibilita"),
							resultSet.getDouble("consumo"),
							resultSet.getInt("id_bacino")
					);
					
					risorse.add(risorsaIdrica);
				}
				
				if (risorse.isEmpty()) {
					logger.info("Nessuna risorsa idrica trovata per il bacino con ID: {}", idBacino);
				} else {
					logger.info("Trovate {} risorse idrica per il bacino con ID: {}", risorse.size(), idBacino);
				}
				
				return risorse;
			}
		} catch (SQLException e) {
			logger.error("Errore durante la raccolta dello storico delle risorse idriche per il bacino con ID: {}",
					idBacino, e);
			return risorse;
		}
	}
	
	
	protected int addRisorsaBacino(RisorsaIdrica risorsaIdrica) {
		int id = 0;
		
		String queryInsert = """
				INSERT INTO risorsa_bacino (consumo, disponibilita, time, id_bacino)
				VALUES (?, ?, ?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(queryInsert,
					Statement.RETURN_GENERATED_KEYS)) {
				statement.setDouble(1, risorsaIdrica.getConsumo());
				statement.setDouble(2, risorsaIdrica.getDisponibilita());
				statement.setString(3, risorsaIdrica.getData());
				statement.setInt(4, risorsaIdrica.getIdSource());
				
				logger.info("Inserimento di una nuova risorsa idrica per il bacino con ID: {}",
						risorsaIdrica.getIdSource());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
						logger.debug("Risorsa idrica aggiunta con ID: {}", id);
					}
				}
			}
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'inserimento di una nuova risorsa idrica", e);
			
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
	
	
	protected void deleteRisorsaBacino(int id) {
		String query = """
				DELETE FROM risorsa_bacino
				WHERE id = ? ;
				""";
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setLong(1, id);
				
				int affectedRows = statement.executeUpdate();
				
				if (affectedRows > 0) {
					logger.info("Risorsa idrica con ID {} eliminata", id);
				} else {
					logger.warn("Nessuna risorsa idrica trovata con ID {}", id);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione della risorsa idrica con ID {}", id, e);
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
	
	
	protected RisorsaIdrica ultimaRisorsa(int idBacino) {
		RisorsaIdrica risorsaIdrica = null;
		
		String query = """
				SELECT * FROM risorsa_bacino
				WHERE id_bacino = ?
				ORDER BY time
				DESC LIMIT 1;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idBacino);
			logger.info("Esecuzione della query per ottenere l'ultima risorsa idrica del bacino con ID {}", idBacino);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					risorsaIdrica = new RisorsaIdrica(
							resultSet.getInt("id"),
							resultSet.getString("time"),
							resultSet.getDouble("disponibilita"),
							resultSet.getDouble("consumo"),
							resultSet.getInt("id_bacino")
					);
					
					logger.debug("Trovata risorsa idrica del bacino: {}", idBacino);
				} else {
					logger.info("Nessuna risorsa idrica del bacino trovata con ID: {}", idBacino);
				}
				
				return risorsaIdrica;
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante la ricerca dell'ultima risorsa idrica per il bacino con ID {}", idBacino, e);
			
			return risorsaIdrica;
		}
	}
	
	
	public void clearRisorse() {
		int totalMonths = 12 + LocalDateTime.now().getMonthValue() - 1;
		
		LocalDateTime result = LocalDateTime.now()
				.minusMonths(totalMonths)
				.with(TemporalAdjusters.firstDayOfMonth());
		
		String retention = result.format(formatterData);
		
		Archive archive = new Archive(this.url, this.archive, "risorsa_bacino", retention);
		
		archive.export();
	}
	
}
