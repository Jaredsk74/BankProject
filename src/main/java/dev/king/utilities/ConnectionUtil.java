package dev.king.utilities;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionUtil {
	
	// Prevent the class from being instantiated
	private ConnectionUtil() {}
	
	public static Connection getConnection() {
		try {
			// Get connection string from properties file
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream("src/main/resources/connection.properties");
			prop.load(in);
			String conStr = prop.getProperty("conStr");
			
			// Attempt to make a connection
			return DriverManager.getConnection(conStr);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
