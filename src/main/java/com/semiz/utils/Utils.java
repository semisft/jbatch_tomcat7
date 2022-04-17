package com.semiz.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Utils {
	private final static Logger logger = Logger.getLogger(Utils.class.getName());


	public static Connection getConnection(String datasourceName) {
		 Connection result = null;
		    try {
		      Context initialContext = new InitialContext();
		      //cast is necessary
		      DataSource datasource = (DataSource)initialContext.lookup("java:comp/env/jdbc/"+datasourceName);
		      if (datasource != null) {
		        result = datasource.getConnection();
		      }
		      else {
		        logger.severe("Failed to lookup datasource.");
		      }
		    }catch (Exception e) {
		    	logger.log(Level.SEVERE, e.getMessage(), e);
		    }
		return result;
	}
	
	public static void createSourceTable(String datasourceName) {
		Connection conn = null;
		try {
			conn = getConnection(datasourceName);
			conn.prepareStatement("CREATE TABLE detail(id int,value int)").execute();
		} catch (Exception e) {
			
		}
		finally {
			try {
				conn.prepareStatement("DELETE FROM detail").execute();
				conn.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	public static void createDestinationTable(String datasourceName) {
		Connection conn = null;
		try {
			conn = getConnection(datasourceName);
			conn.prepareStatement("CREATE TABLE summary(id int,total bigint)").execute();
		} catch (Exception e) {
			
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
}
