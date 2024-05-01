package pissir.watermanager.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Archive {
	
	private static final Logger logger = LogManager.getLogger(Archive.class.getName());
	private static final Logger loggerSql = LogManager.getLogger("sql");
	private Connection connectionSrc;
	private Connection connectionTgt;
	private final String source;
	private final String target;
	private final String table;
	private final String retention;
	
	
	public Archive(String url, String tgt, String table, String retention) {
		this.source = url;
		this.target = tgt;
		this.table = table;
		this.retention = retention;
	}
	
	
	public int export() {
		try {
			this.connectionSrc = DriverManager.getConnection(this.source);
			this.connectionTgt = DriverManager.getConnection(this.target);
			
			return this.readData();
		} catch (Exception e) {
			return - 1;
		}
	}
	
	
	public int readData()
			throws Exception {
		int columns;
		int size = 0;
		
		String query = "SELECT * FROM " + this.table + " WHERE insert_time < ?;";
		
		LinkedHashMap<String, Object> row;
		ResultSetMetaData resultSetMetaData;
		
		this.connectionTgt.setAutoCommit(false);
		
		try (PreparedStatement statement = this.connectionSrc.prepareStatement(query)) {
			loggerSql.debug("Esecuzione query: " + query);
			loggerSql.debug("Params: ");
			loggerSql.debug("?1 " + this.table + " < " + this.retention);
			
			statement.setString(1, this.retention);
			
			try (ResultSet resultSet = statement.executeQuery()) {
				
				resultSetMetaData = resultSet.getMetaData();
				columns = resultSetMetaData.getColumnCount();
				
				while (resultSet.next()) {
					row = new LinkedHashMap<>(columns);
					
					for (int i = 1; i <= columns; ++ i) {
						row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
					
					size++;
					this.processaRecord(row);
				}
			} catch (Exception e) {
				this.connectionTgt.rollback();
				
				throw e;
			}
			
			this.connectionTgt.commit();
			
			logger.debug("Fine scrittura dati");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			loggerSql.error(e.getMessage(), e);
			
			throw e;
		} finally {
			this.connectionSrc.close();
			this.connectionTgt.close();
		}
		
		return size;
	}
	
	
	public void processaRecord(LinkedHashMap<String, Object> record)
			throws Exception {
		if (record.isEmpty()) {
			return;
		}
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("INSERT INTO ");
		builder.append(this.table);
		builder.append(" (");
		
		
		LinkedList<String> columns = new LinkedList<>(record.keySet());
		
		for (int j = 0; j < columns.size(); j++) {
			builder.append(columns.get(j));
			
			if (j < columns.size() - 1) {
				builder.append(", ");
			}
		}
		
		builder.append(")\n");
		builder.append("VALUES (");
		
		for (int i = 0; i < columns.size(); i++) {
			builder.append("?");
			
			if (i < columns.size() - 1) {
				builder.append(", ");
			}
		}
		
		builder.append(")");
		
		String query = builder.toString();
		
		try (PreparedStatement statement = this.connectionTgt.prepareStatement(query)) {
			
			for (int j = 1; j < columns.size() + 1; j++) {
				statement.setObject(j, record.get(columns.get(j - 1)));
			}
			
			statement.executeUpdate();
		} catch (Exception e) {
			
			throw e;
		}
	}
	
}
