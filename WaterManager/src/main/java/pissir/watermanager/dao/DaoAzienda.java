package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import pissir.watermanager.model.item.Azienda;

import java.sql.*;
import java.util.HashSet;

/**
 * @author Almasio Luca
 * @author Borova Dritan
 * @author Gattico Alessandro
 */

@Repository
public class DaoAzienda {
	
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Database/DATABASEWATER";
	private static final Logger logger = LogManager.getLogger(DaoAzienda.class.getName());
	//private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/WaterManager/src/main/resources/DATABASEWATER";
	
	
	protected DaoAzienda() {
	}
	
	
	protected Azienda getAziendaId(int id) {
		Azienda azienda = null;
		
		String query = """
				SELECT *
				FROM azienda
				WHERE id = ? ;
				""";
		
		logger.info("Estrazione azienda con ID {}", id);
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setLong(1, id);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					azienda = new Azienda(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getInt("id_user")
					);
					
					logger.info("Azienda trovata: {}", azienda.getNome());
				} else {
					logger.info("Nessuna azienda trovata con ID {}", id);
				}
				
				return azienda;
			}
			
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'azienda con ID {}", id, e);
			
			return azienda;
		}
	}
	
	
	protected Azienda getAziendaNome(String nome) {
		Azienda azienda = null;
		
		String query = """
				SELECT *
				FROM azienda
				WHERE nome = ? ;
				""";
		
		logger.info("Estrazione azienda con nome {}", nome);
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			
			statement.setString(1, nome);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					azienda = new Azienda(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							resultSet.getInt("id_user")
					);
					
					logger.info("Azienda {} trovata", azienda.getNome());
				} else {
					logger.info("Nessuna azienda trovata con nome {}", nome);
				}
				
				return azienda;
			}
		} catch (SQLException e) {
			logger.error("Errore durante il recupero dell'azienda con nome {}", nome, e);
			
			return azienda;
		}
	}
	
	
	protected HashSet<Azienda> getAziende() {
		HashSet<Azienda> aziende = new HashSet<>();
		
		String query = """
				SELECT *
				FROM azienda;
				""";
		
		logger.info("Estrazione di tutte le aziende");
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query);
			 ResultSet resultSet = statement.executeQuery()) {
			
			while (resultSet.next()) {
				Azienda azienda = new Azienda(
						resultSet.getInt("id"),
						resultSet.getString("nome"),
						resultSet.getInt("id_user")
				);
				
				aziende.add(azienda);
			}
			
			if (aziende.isEmpty()) {
				logger.info("Nessuna azienda trovata nel database");
			} else {
				logger.info("Numero di aziende trovate: {}", aziende.size());
			}
			
			return aziende;
		} catch (SQLException e) {
			logger.error("Errore durante il recupero delle aziende", e);
			return aziende;
		}
	}
	
	
	protected int addAzienda(Azienda azienda) {
		int id = 0;
		String query = """
				INSERT INTO azienda (nome, id_user)
				VALUES (?, ?);
				""";
		
		logger.info("Aggiunta azienda con nome {}", azienda.getNome());
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, azienda.getNome());
				statement.setInt(2, azienda.getIdGestore());
				
				statement.executeUpdate();
				
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						id = resultSet.getInt(1);
						
						logger.info("Azienda inserita con ID: {}", id);
					}
				}
			}
			connection.commit();
		} catch (Exception e) {
			logger.error("Errore durante l'aggiunta dell'azienda {}.", azienda.getNome(), e);
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException ex) {
					logger.error("Errore durante l'esecuzione del rollback", ex);
				}
			}
			
			throw new RuntimeException("Errore durante l'aggiunta dell'azienda", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Errore nella chiusura della connessione", e);
				}
			}
		}
		
		return id;
	}
	
	
	protected void deleteAzienda(int id) {
		String query = """
				DELETE FROM azienda
				WHERE id = ? ;
				""";
		
		logger.info("Eliminazione azienda con ID {}", id);
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(this.url);
			connection.setAutoCommit(false);
			
			logger.info("Eliminazione dell'azienda con ID: {}", id);
			
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setInt(1, id);
				
				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					logger.debug("Azienda {} eliminata con successo.", id);
				} else {
					logger.info("Nessun azienda trovata con ID: {}.", id);
				}
			}
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("Errore durante l'eliminazione dell'azienda con ID: {}", id, e);
			
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
	
	
	protected Azienda getAziendaUser(int idGestore) {
		Azienda azienda = null;
		String query = """
				SELECT *
				FROM azienda
				WHERE id_user = ? ;
				""";
		
		logger.info("Estrazione azienda con gestore {}", idGestore);
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, idGestore);
			
			logger.info("Esecuzione della query per ottenere l'azienda con ID gestore: {}", idGestore);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					azienda = new Azienda(
							resultSet.getInt("id"),
							resultSet.getString("nome"),
							idGestore
					);
					
					logger.info("Azienda {} trovata per il gestore con ID {}", azienda.getNome(), idGestore);
				} else {
					logger.info("Nessuna azienda trovata per il gestore con ID {}", idGestore);
				}
				
				return azienda;
			}
			
			
		} catch (SQLException e) {
			logger.error("Errore durante la ricerca dell'azienda per il gestore con ID {}", idGestore, e);
			
			return azienda;
		}
	}
	
	
}
