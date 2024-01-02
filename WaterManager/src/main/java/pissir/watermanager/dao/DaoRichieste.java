package pissir.watermanager.dao;

import pissir.watermanager.model.item.RichiestaIdrica;
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
public class DaoRichieste {
	
	private final Logger logger = LogManager.getLogger(DaoRichieste.class.getName());
	private final Logger loggerSql = LogManager.getLogger("sql");
	private final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/src/main/resources/DATABASEWATER";
	
	
	public DaoRichieste () {
	}
	
	
	public RichiestaIdrica getRichiestaId (int idRichiesta) {
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		RichiestaIdrica richiestaIdrica = null;
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id = " + idRichiesta);
			
			statement.setInt(1, idRichiesta);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new HashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					richiestaIdrica = new RichiestaIdrica((int) row.get("id"), (Double) row.get("quantita"),
							(int) row.get("id_coltivazione"), (int) row.get("id_bacino"), (String) row.get("date"));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return richiestaIdrica;
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteColtivazione (int idColtivazione) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RichiestaIdrica> richieste = new HashSet<>();
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id_coltivazione = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id_coltivazione = " + idColtivazione);
			
			statement.setInt(1, idColtivazione);
			
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
					RichiestaIdrica richiestaIdrica =
							new RichiestaIdrica((int) map.get("id"), (Double) map.get("quantita"),
									(int) map.get("id_coltivazione"), (int) map.get("id_bacino"),
									(String) map.get("date"));
					
					richieste.add(richiestaIdrica);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return richieste;
	}
	
	
	public HashSet<RichiestaIdrica> getRichiesteBacino (int idBacino) {
		ArrayList<HashMap<String, Object>> list;
		int columns;
		HashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		HashSet<RichiestaIdrica> richieste = new HashSet<>();
		
		String query = """
				SELECT *
				FROM richiesta
				WHERE id_bacino = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id_bacino = " + idBacino);
			
			statement.setInt(1, idBacino);
			
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
					RichiestaIdrica richiestaIdrica =
							new RichiestaIdrica((int) map.get("id"), (Double) map.get("quantita"),
									(int) map.get("id_coltivazione"), (int) map.get("id_bacino"),
									(String) map.get("date"));
					
					richieste.add(richiestaIdrica);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
		}
		
		return richieste;
	}

    /*
    public HashSet<RichiestaIdrica> getRichiesteAzienda(int uuidAzienda) {
        ArrayList<HashMap<String, Object>> list;
        int columns;
        HashMap<String, Object> row;
        ResultSetMetaData resultSetMetaData;
        HashSet<RichiestaIdrica> richieste = new HashSet<>();

        String query = """
                SELECT *
                FROM richiesta_idrica
                JOIN Coltivazione ON richiesta_idrica.coltivazione_uuid = Coltivazione.UUID
                JOIN Campo ON Coltivazione.campo_uuid = Campo.UUID
                JOIN Campagna ON Campo.campagna_uuid = Campagna.UUID
                JOIN Azienda ON Campagna.azienda_uuid = Azienda.UUID
                WHERE Azienda.UUID = ?;
                """;

        try (Connection connection = DriverManager.getConnection(this.url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setint(1, uuidAzienda);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSetMetaData = resultSet.getMetaData();
                columns = resultSetMetaData.getColumnCount();
                list = new ArrayList<>();

                while (resultSet.next()) {
                    row = new HashMap<>(columns);

                    for (int i = 1; i <= columns; ++i) {
                        row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                    }

                    list.add(row);
                }

                for (HashMap<String, Object> map : list) {
                    int id = (int) map.get("IdRichiesta");
                    int bacinoIdricoUUID = (int) map.get("BacinoIdricoUUID");
                    Double quantita = (Double) map.get("Quantita");
                    int uuidColtivazione = (int) map.get("ColtivazioneUUID");
                    String data = (String) map.get("Data");
					/*LocalDateTime date;

					Pattern p = Pattern.pissirpile("(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})");
					Matcher m = p.matcher(data);

					boolean formato = m.matches();

					if (formato) {
						int anno = Integer.parseInt(m.group(1));
						int mese = Integer.parseInt(m.group(2));
						int giorno = Integer.parseInt(m.group(3));
						int ora = Integer.parseInt(m.group(4));
						int minuti = Integer.parseInt(m.group(5));
						int secondi = Integer.parseInt(m.group(6));
						date = LocalDateTime.of(anno, mese, giorno, ora, minuti, secondi);
					} else {
						date = null;
					}



                    RichiestaIdrica RichiestaIdrica =
                            new RichiestaIdrica(id, bacinoIdricoUUID, uuidColtivazione, quantita, data);
                    richieste.add(RichiestaIdrica);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            loggerSql.error(e.getMessage(), e);
        }

        return richieste;
    }


    public HashSet<RichiestaIdrica> getRichiesteCampo(int uuidCampo) {
        ArrayList<HashMap<String, Object>> list;
        int columns;
        HashMap<String, Object> row;
        ResultSetMetaData resultSetMetaData;
        HashSet<RichiestaIdrica> richieste = new HashSet<>();

        String query = """
                SELECT *
                FROM richiesta_idrica
                JOIN Coltivazione ON richiesta_idrica.coltivazione_uuid = Coltivazione.UUID
                JOIN Campo ON Coltivazione.campo_uuid = Campo.UUID
                WHERE Campo.UUID = ?;
                """;

        try (Connection connection = DriverManager.getConnection(this.url);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setint(1, uuidCampo);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSetMetaData = resultSet.getMetaData();
                columns = resultSetMetaData.getColumnCount();
                list = new ArrayList<>();

                while (resultSet.next()) {
                    row = new HashMap<>(columns);

                    for (int i = 1; i <= columns; ++i) {
                        row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                    }

                    list.add(row);
                }

                for (HashMap<String, Object> map : list) {
                    int id = (int) map.get("IdRichiesta");
                    int bacinoIdricoUUID = (int) map.get("BacinoIdricoUUID");
                    Double quantita = (Double) map.get("Quantita");
                    int uuidColtivazione = (int) map.get("ColtivazioneUUID");
                    String data = (String) map.get("Data");
					/*LocalDateTime date;

					Pattern p = Pattern.pissirpile("(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})");
					Matcher m = p.matcher(data);

					boolean formato = m.matches();

					if (formato) {
						int anno = Integer.parseInt(m.group(1));
						int mese = Integer.parseInt(m.group(2));
						int giorno = Integer.parseInt(m.group(3));
						int ora = Integer.parseInt(m.group(4));
						int minuti = Integer.parseInt(m.group(5));
						int secondi = Integer.parseInt(m.group(6));
						date = LocalDateTime.of(anno, mese, giorno, ora, minuti, secondi);
					} else {
						date = null;
					}



                    RichiestaIdrica RichiestaIdrica =
                            new RichiestaIdrica(id, bacinoIdricoUUID, uuidColtivazione, quantita, data);
                    richieste.add(RichiestaIdrica);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            loggerSql.error(e.getMessage(), e);
        }

        return richieste;
    }

     */
	
	
	public int addRichiesta (RichiestaIdrica richiesta) {
		int id = 0;
		String query = """
				INSERT INTO richiesta (quantita, id_coltivazione, id_bacino, date)
				VALUES (?, ?, ?, ?);
				SELECT last_insert_rowid() AS newId;
				""";
		
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 quantita = " + richiesta.getQuantita());
			loggerSql.debug("?2 id_coltivazione = " + richiesta.getIdColtivazione());
			loggerSql.debug("?3 id_bacino = " + richiesta.getIdBacino());
			loggerSql.debug("?4 date = " + richiesta.getDate());
			
			statement.setDouble(1, richiesta.getQuantita());
			statement.setInt(2, richiesta.getIdColtivazione());
			statement.setInt(3, richiesta.getIdBacino());
			statement.setString(4, richiesta.getDate());
			
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
	
	
	public void deleteRichiesta (int idRichiesta) {
		String query = """
				DELETE FROM richiesta
				WHERE id = ? ;
				""";
		
		try (Connection connection = DriverManager.getConnection(this.url);
			 PreparedStatement statement = connection.prepareStatement(query)) {
			loggerSql.debug("Executing sql " + query);
			loggerSql.debug("Parameters: ");
			loggerSql.debug("?1 id = " + idRichiesta);
			
			statement.setInt(1, idRichiesta);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
		}
	}
	
}
