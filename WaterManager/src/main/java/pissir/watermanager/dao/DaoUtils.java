package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pissir.watermanager.model.user.UserRole;

import java.sql.*;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */


public class DaoUtils {
	
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/DATABASEWATER";
	private static final Logger logger = LogManager.getLogger(DaoUtils.class.getName());
	
	
	protected DaoUtils() {
	}
	
	
	protected HashSet<String> getRaccolti() {
		HashSet<String> raccolti = new HashSet<>();
		
		String query = """
				SELECT *
				FROM raccolto;
				""";
		
		try (Connection connectionection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connectionection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			logger.info("Estrazione tipologie di raccolto");
			
			while (resultSet.next()) {
				raccolti.add(resultSet.getString("nome"));
			}
			
			if (! raccolti.isEmpty()) {
				logger.info("Trovate {} tipologie di raccolto", raccolti.size());
			} else {
				logger.info("Non sono state trovtate tipologie di raccolto");
			}
			
			return raccolti;
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei raccolti", e);
			
			return raccolti;
		}
	}
	
	
	protected void addRaccolto(String raccolto) {
		String query = """
				INSERT INTO raccolto (nome)
				VALUES (?);
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Inserimento della nuova tipologia di raccolto: {}", raccolto);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, raccolto);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Raccolto '{}' aggiunta con successo.", raccolto);
				} else {
					logger.info("Nessun inserimento effettuato per il raccolto '{}'.", raccolto);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'inserimento del raccolto '{}'", raccolto, e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
	}
	
	
	protected void deleteRaccolto(String raccolto) {
		String query = """
				DELETE FROM raccolto
				WHERE nome = ? ;
				""";
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Eliminazione del raccolto: {}", raccolto);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, raccolto);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Raccolto '{}' eliminato con successo.", raccolto);
				} else {
					logger.info("Nessun raccolto trovato con nome '{}'.", raccolto);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione del raccolto '{}'", raccolto, e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
	}
	
	
	protected HashSet<String> getEsigenze() {
		HashSet<String> esigenze = new HashSet<>();
		
		String query = """
				SELECT *
				FROM esigenza;
				""";
		
		try (Connection connectionection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connectionection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			logger.info("Estrazione tipologie di esigenza idrica");
			
			while (resultSet.next()) {
				esigenze.add(resultSet.getString("nome"));
			}
			
			if (! esigenze.isEmpty()) {
				logger.info("Trovate {} esigenze", esigenze.size());
			} else {
				logger.info("Non sono stati trovtate esigenze");
			}
			
			return esigenze;
		} catch (SQLException e) {
			return esigenze;
		}
	}
	
	
	protected void addEsigenza(String esigenza) {
		String query = """
				INSERT INTO esigenza (nome)
				VALUES (?);
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Inserimento della nuova esigenza: {}", esigenza);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, esigenza);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Esigenza '{}' aggiunta con successo.", esigenza);
				} else {
					logger.info("Nessun inserimento effettuato per l'esigenza '{}'.", esigenza);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'inserimento dell'esigenza '{}'", esigenza, e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
	}
	
	
	protected void deleteEsigenza(String esigenza) {
		String query = """
				DELETE FROM esigenza
				WHERE nome = ? ;
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Eliminazione del esigenza: {}", esigenza);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, esigenza);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Esigenza '{}' eliminato con successo.", esigenza);
				} else {
					logger.info("Nessuna esigenza trovato con nome '{}'.", esigenza);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione del esigenza '{}'", esigenza, e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
	}
	
	
	protected HashSet<String> getIrrigazioni() {
		HashSet<String> irrigazioni = new HashSet<>();
		
		String query = """
				SELECT *
				FROM irrigazione;
				""";
		
		try (Connection connectionection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connectionection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			logger.info("Estrazione tipologie di irrigazione");
			
			while (resultSet.next()) {
				irrigazioni.add(resultSet.getString("nome"));
			}
			
			if (! irrigazioni.isEmpty()) {
				logger.info("Trovate {} irrigazioni", irrigazioni.size());
			} else {
				logger.info("Non sono stati trovtate irrigazioni");
			}
			
			return irrigazioni;
		} catch (SQLException e) {
			return irrigazioni;
		}
	}
	
	
	protected void addIrrigazione(String irrigazione) {
		String query = """
				INSERT INTO irrigazione (nome)
				VALUES (?);
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Inserimento della nuova irrigazione: {}", irrigazione);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, irrigazione);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Irrigazione '{}' aggiunta con successo.", irrigazione);
				} else {
					logger.info("Nessun inserimento effettuato per l'irrigazione '{}'.", irrigazione);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'inserimento dell'irrigaizone '{}'", irrigazione, e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
	}
	
	
	protected void deleteIrrigazione(String irrigazine) {
		String query = """
				DELETE FROM irrigazione
				WHERE nome = ? ;
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Eliminazione dell'irrigazione: {}", irrigazine);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, irrigazine);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Irrigazione '{}' eliminato con successo.", irrigazine);
				} else {
					logger.info("Nessuna irrigazione trovata con nome '{}'.", irrigazine);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione dell'irrigazione '{}'", irrigazine, e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
	}
	
	
	protected HashSet<String> getSensorTypes() {
		HashSet<String> types = new HashSet<>();
		
		String query = """
				SELECT *
				FROM sensor_type;
				""";
		
		try (Connection connectionection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connectionection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			logger.info("Estrazione tipologie di sensore");
			
			while (resultSet.next()) {
				types.add(resultSet.getString("type"));
			}
			
			if (! types.isEmpty()) {
				logger.info("Trovate {} tipologie di sensore", types.size());
			} else {
				logger.info("Non sono state trovtate tipologie di sensore");
			}
			
			return types;
		} catch (SQLException e) {
			return types;
		}
	}
	
	
	protected void addSensorType(String tipologia) {
		String query = """
				INSERT INTO sensor_type (type)
				VALUES (?);
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Inserimento della nuova tipologia di sensore: {}", tipologia);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, tipologia);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Irrigazione '{}' aggiunta con successo.", tipologia);
				} else {
					logger.info("Nessun inserimento effettuato per l'irrigazione '{}'.", tipologia);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'inserimento dell'irrigaizone '{}'", tipologia, e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
	}
	
	
	protected void deleteSensorType(String type) {
		String query = """
				DELETE FROM sensor_type
				WHERE type = ? ;
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Eliminazione tipologia di sensore: {}", type);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, type);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Tipologia sensore '{}' eliminata con successo.", type);
				} else {
					logger.info("Nessuna tipologia sensore trovata con nome '{}'.", type);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione della tipologia sensore '{}'", type, e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
	}
	
	
	protected int countGestori(UserRole userRole) {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM users
				WHERE role = ?;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			
			logger.info("Estrazione numero gestori {}", userRole);
			
			statement.setString(1, String.valueOf(userRole));
			
			if (resultSet.next()) {
				count = resultSet.getInt("total");
				
				logger.info("Trovati {} {}", count, userRole);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countRaccolti() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM raccolto;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			
			logger.info("Estrazione numero raccolti");
			
			if (resultSet.next()) {
				count = resultSet.getInt("total");
				
				logger.info("Trovati {} raccolti", count);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countEsigenze() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM esigenza;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			
			logger.info("Estrazione numero esigenze");
			
			if (resultSet.next()) {
				count = resultSet.getInt("total");
				
				logger.info("Trovate {} esigenze", count);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countIrrigazioni() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM irrigazione;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			
			logger.info("Estrazione numero irrigazioni");
			
			if (resultSet.next()) {
				count = resultSet.getInt("total");
				
				logger.info("Trovate {} irrigazioni", count);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countSensorTypes() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM sensor_type;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			
			logger.info("Estrazione numero tipologie sensori");
			
			if (resultSet.next()) {
				count = resultSet.getInt("total");
				
				logger.info("Trovate {} tipologie sensore", count);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countCampi() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM campo;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			
			logger.info("Estrazione numero campi");
			
			if (resultSet.next()) {
				count = resultSet.getInt("total");
				
				logger.info("Trovati {} campi", count);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countCampagne() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM campagna;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			
			logger.info("Estrazione numero campagne");
			
			if (resultSet.next()) {
				count = resultSet.getInt("total");
				
				logger.info("Trovate {} campagne", count);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countAziende() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM azienda;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			
			logger.info("Estrazione numero aziende");
			
			if (resultSet.next()) {
				count = resultSet.getInt("total");
				
				logger.info("Trovate {} aziende", count);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
	
	protected int countBacini() {
		int count = 0;
		String query = """
				SELECT COUNT(*)
				AS total
				FROM bacino;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			
			logger.info("Estrazione numero bacini idrici");
			
			if (resultSet.next()) {
				count = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return count;
	}
	
}
