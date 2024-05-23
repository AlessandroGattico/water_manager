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
public class DaoRisorseAzienda {
	
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/DATABASEWATER";
	private final String archive = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/ARCHIVE";
	
	//private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	//private final String archive = "jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/ARCHIVE";
	private static final Logger logger = LogManager.getLogger(DaoRisorseAzienda.class.getName());
	private static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	protected DaoRisorseAzienda() {
	}
	
	
	protected RisorsaIdrica getRisorsaAziendaId(int idRisorsa) {
		RisorsaIdrica risorsaIdrica = null;
		
		String query = """
				SELECT *
				FROM risorsa_azienda
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idRisorsa);
			
			logger.info("Esecuzione della query per ottenere la risorsa idrica aziendale con ID: {}", idRisorsa);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				
				if (resultSet.next()) {
					risorsaIdrica = new RisorsaIdrica(
							resultSet.getInt("id"),
							resultSet.getString("time"),
							resultSet.getDouble("disponibilita"),
							resultSet.getDouble("consumo"),
							resultSet.getInt("id_azienda")
					);
					
					logger.debug("Trovata risorsa idrica: {}", idRisorsa);
				} else {
					logger.info("Nessuna risorsa idrica aziendale trovata con ID: {}", idRisorsa);
				}
				
				return risorsaIdrica;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della risorsa idrica aziendale con ID: {}", idRisorsa, e);
			
			return risorsaIdrica;
		}
	}
	
	
	protected HashSet<RisorsaIdrica> getStoricoRisorseAzienda(int idAzienda) {
		HashSet<RisorsaIdrica> risorse = new HashSet<>();
		RisorsaIdrica risorsaIdrica = null;
		
		String query = """
				SELECT *
				FROM risorsa_azienda
				WHERE id_azienda = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idAzienda);
			
			logger.info(
					"Esecuzione della query per ottenere lo storico delle risorse idriche aziendali con ID azienda: {}",
					idAzienda);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					risorsaIdrica = new RisorsaIdrica(
							resultSet.getInt("id"),
							resultSet.getString("time"),
							resultSet.getDouble("disponibilita"),
							resultSet.getDouble("consumo"),
							resultSet.getInt("id_azienda")
					);
					
					risorse.add(risorsaIdrica);
				}
				
				if (risorse.isEmpty()) {
					logger.info("Nessuna risorsa idrica trovata per l'azienda con ID: {}", idAzienda);
				} else {
					logger.debug("Risorse idriche trovate per l'azienda con ID {}: {}", idAzienda,
							risorse.size());
				}
				
				return risorse;
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dello storico delle risorse idriche dell'azienda con ID:" +
					" {}", idAzienda, e);
			
			return risorse;
		}
	}
	
	
	protected int addRisorsaAzienda(RisorsaIdrica risorsaIdrica) {
		int id = 0;
		Connection connection = null;
		
		String query = """
				INSERT INTO risorsa_azienda (consumo, disponibilita, time, id_azienda)
				VALUES (?, ?, ?, ?);
				""";
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setDouble(1, risorsaIdrica.getConsumo());
				statement.setDouble(2, risorsaIdrica.getDisponibilita());
				statement.setString(3, risorsaIdrica.getData());
				statement.setInt(4, risorsaIdrica.getIdSource());
				
				logger.info("Esecuzione della query per aggiungere una nuova risorsa idrica aziendale");
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
					}
				}
			}
			
			connection.commit();
			
			logger.info("Risorsa idrica aziendale aggiunta con successo, ID: {}", id);
		} catch (Exception e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException ex) {
				logger.error("Errore durante il rollback dell'aggiunta della risorsa idrica aziendale", ex);
			}
			
			logger.error("Errore durante l'aggiunta della risorsa idrica aziendale", e);
			
			throw new RuntimeException("Errore durante l'aggiunta della risorsa idrica aziendale", e);
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
	
	
	protected void deleteRisorsaAzienda(int id) {
		Connection connection = null;
		String query = """
				DELETE FROM risorsa_azienda
				WHERE id = ? ;
				""";
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, id);
				
				logger.info("Esecuzione della query per eliminare la risorsa idrica aziendale con ID: {}", id);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.info("Risorsa idrica aziendale eliminata con successo");
				} else {
					logger.info("Nessuna risorsa idrica aziendale trovata con ID: {}", id);
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
	
	
	protected RisorsaIdrica ultimaRisorsa(int idAzienda) {
		RisorsaIdrica risorsa = null;
		
		String query = """
				SELECT * FROM risorsa_azienda
				WHERE id_azienda = ?
				ORDER BY time DESC LIMIT 1;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setInt(1, idAzienda);
			
			logger.info("Esecuzione della query per ottenere l'ultima risorsa idrica aziendale con ID azienda: {}",
					idAzienda);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				
				if (resultSet.next()) {
					risorsa = new RisorsaIdrica(
							resultSet.getInt("id"),
							resultSet.getString("time"),
							resultSet.getDouble("disponibilita"),
							resultSet.getDouble("consumo"),
							idAzienda
					);
					
					logger.debug("Trovata ultima risorsa idrica aziendale: {}", risorsa);
				} else {
					logger.info("Nessuna risorsa idrica aziendale trovata per l'ID azienda: {}", idAzienda);
				}
				
				return risorsa;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'ultima risorsa idrica aziendale per l'ID azienda: {}",
					idAzienda, e);
			
			return risorsa;
		}
	}
	
	
	public void clearRisorse() {
		int totalMonths = 12 + LocalDateTime.now().getMonthValue() - 1;
		
		LocalDateTime result = LocalDateTime.now()
				.minusMonths(totalMonths)
				.with(TemporalAdjusters.firstDayOfMonth());
		
		String retention = result.format(formatterData);
		
		Archive archive = new Archive(this.url, this.archive, "risorsa_azienda", retention);
		
		archive.export();
		
	}
	
}
