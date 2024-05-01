package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Coltivazione;

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
public class DaoColtivazione {
	
	private final String url =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	private final String archive =
			"jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/ARCHIVE";
	private static final Logger logger = LogManager.getLogger(DaoColtivazione.class.getName());
	private static final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	protected DaoColtivazione() {
	}
	
	
	protected Coltivazione getColtivazioneId(int idColtivazione) {
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
				if (resultSet.next()) {
					coltivazione = new Coltivazione(
							resultSet.getInt("id"),
							resultSet.getString("raccolto"),
							resultSet.getString("irrigazione"),
							resultSet.getString("esigenza"),
							resultSet.getDouble("temperatura"),
							resultSet.getDouble("umidita"),
							resultSet.getString("semina"),
							resultSet.getInt("id_campo")
					);
					
					logger.debug("Trovata coltivazione: {}", coltivazione.getRaccolto());
				} else {
					logger.info("Nessuna coltivazione trovata con ID: {}", idColtivazione);
				}
				
				return coltivazione;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero della coltivazione con ID: {}", idColtivazione, e);
			
			return coltivazione;
		}
	}
	
	
	protected HashSet<Coltivazione> getColtivazioniCampo(int idCampo) {
		HashSet<Coltivazione> coltivazioni = new HashSet<>();
		Coltivazione coltivazione = null;
		
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
				while (resultSet.next()) {
					coltivazione = new Coltivazione(
							resultSet.getInt("id"),
							resultSet.getString("raccolto"),
							resultSet.getString("irrigazione"),
							resultSet.getString("esigenza"),
							resultSet.getDouble("temperatura"),
							resultSet.getDouble("umidita"),
							resultSet.getString("semina"),
							resultSet.getInt("id_campo")
					);
					
					coltivazioni.add(coltivazione);
				}
				
				if (coltivazioni.isEmpty()) {
					logger.info("Nessuna coltivazione trovata per il campo con ID: {}", idCampo);
				} else {
					logger.debug("Trovate {} coltivazione per il campo con ID: {}", coltivazione.getRaccolto());
				}
				
				return coltivazioni;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle coltivazioni per il campo con ID: {}", idCampo, e);
			
			return coltivazioni;
		}
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
	
	
	public void clearColtivazioni() {
		int totalMonths = 3 * 12 + LocalDateTime.now().getMonthValue() - 1;
		
		LocalDateTime result = LocalDateTime.now()
				.minusMonths(totalMonths)
				.with(TemporalAdjusters.firstDayOfMonth());
		
		String retention = result.format(formatterData);
		
		Archive archive = new Archive(this.url, this.archive, "coltivazione", retention);
		
		archive.export();
	}
	
}
