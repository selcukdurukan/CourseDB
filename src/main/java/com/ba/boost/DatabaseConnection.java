package com.ba.boost;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

	private static DatabaseConnection instance;
	private Connection con;
	private Properties props;
	private String propertiesFile;

	private DatabaseConnection() {
		super();
	}

	public static DatabaseConnection getInstance() {
		if (DatabaseConnection.instance == null) {
			DatabaseConnection.instance = new DatabaseConnection();
		}
		return DatabaseConnection.instance;
	}

	public Connection getCon() {
		if (this.con == null) {
			try {
				con = DriverManager.getConnection(this.getUrl());
			} catch (SQLException e) {
				System.err.println("Cannot connect to db. " + "<<<< " + e.getMessage() + " >>>>");
				System.exit(1234);
			}
		}
		return con;
	}

	private String getUrl() {
		this.props = this.getProperties();
//		"jdbc:postgresql://localhost:5432/northwind?user=postgres&password=root"; 
		StringBuilder sb = new StringBuilder("jdbc:");
		sb.append(this.props.getProperty("db.driver")).append("://").append(this.props.getProperty("db.server"))
				.append(":").append(this.props.getProperty("db.port")).append("/")
				.append(this.props.getProperty("db.name")).append("?user=" + this.props.getProperty("db.user.name"))
				.append("&password=" + this.props.getProperty("db.user.password"));

		return sb.toString();
	}

	private Properties getProperties() {
		if (this.props == null) {
			if (this.propertiesFile == null) {
				System.err.println("Dosya tanimli degil.");
				System.exit(404);
			}
			try (InputStream is = new FileInputStream(this.propertiesFile)) {
				this.props = new Properties();
				this.props.load(is);
			} catch (IOException e) {
				System.err.println("Dosya okunamadi.");
				System.exit(303);
			}
		}

		return this.props;
	}

	public String getPropertiesFile() {
		return propertiesFile;
	}

	public void setPropertiesFile(String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}


}
