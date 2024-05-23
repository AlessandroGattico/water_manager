package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Campo;

import java.sql.*;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoCampo {
	
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/DATABASEWATER";
	
	//private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	private static final Logger logger = LogManager.getLogger(DaoCampo.class.getName());
	
	
	protected DaoCampo() {
	}
	
	
	protected Campo getCampoId(int idCampo) {
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
				if (resultSet.next()) {
					campo = new Campo(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getDouble("dimensione"),
							resultSet.getInt("id_campagna")
					);
					
					logger.debug("Trovato campo: {}", campo.getNome());
				} else {
					logger.info("Nessun campo trovato con ID: {}", idCampo);
				}
				
				return campo;
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante la ricerca del campo con ID: {}", idCampo, e);
			
			return campo;
		}
	}
	
	
	protected HashSet<Campo> getCampiCampagna(int idCampagna) {
		HashSet<Campo> campi = new HashSet<>();
		Campo campo = null;
		
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
				while (resultSet.next()) {
					campo = new Campo(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getDouble("dimensione"),
							resultSet.getInt("id_campagna")
					);
					
					campi.add(campo);
				}
				
				if (campi.isEmpty()) {
					logger.info("Nessun campo trovato per la campagna con ID: {}", idCampagna);
					
					
				} else {
					logger.info("Trovati {} campi per la campagna con ID: {}", campi.size(), idCampagna);
				}
				return campi;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei campi per la campagna con ID: {}", idCampagna, e);
			
			return campi;
		}
	}
	
	
	protected int addCampo(Campo campo) {
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
	
	
	protected void deleteCampo(int idCampo) {
		String query = """
				DELETE FROM campo
				WHERE id = ? ;
				""";
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Tentativo di eliminazione del raccolto: {}", idCampo);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, idCampo);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Raccolto '{}' eliminato con successo.", idCampo);
				} else {
					logger.info("Nessun raccolto trovato con nome '{}'.", idCampo);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione del raccolto '{}'", idCampo, e);
			
			if (connection != null) {
				try {
					connection.rollback();
					
					logger.info("Rollback eseguito a seguito di un errore.");
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
			
			throw new RuntimeException("Errore durante l'eliminazione del raccolto", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
					
					logger.info("Connessione chiusa.");
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
	}
	
	
	protected HashSet<Campo> getCampi() {
		HashSet<Campo> campi = new HashSet<>();
		Campo campo = null;
		
		String query = """
				SELECT *
				FROM campo;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			logger.info("Recupero tutti i campi");
			
			try (ResultSet resultSet = statement.executeQuery()) {
				
				while (resultSet.next()) {
					campo = new Campo(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getDouble("dimensione"),
							resultSet.getInt("id_campagna")
					);
					
					campi.add(campo);
				}
				
				if (campi.isEmpty()) {
					logger.info("Nessun campo trovata");
				} else {
					logger.debug("Numero di campi trovati: {}", campi.size());
				}
				
				return campi;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dei campi", e);
			
			return campi;
		}
	}
	
	
	protected boolean existsCampoCampagna(int id, String campo) {
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
			
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				boolean exists = resultSet.getInt(1) > 0;
				
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
