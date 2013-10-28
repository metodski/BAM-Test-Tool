package com.sirma.itt.bam.testtool.matcher;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Run the database to csv file program.
 * 
 * @author N.Velkov
 */
public final class RunProgram {

	/**
	 * Disallowing instantiation.
	 */
	private RunProgram() {
	}

	private static final Logger LOGGER = Logger.getLogger(RunProgram.class.getName());

	/**
	 * Establish the database connection, execute the given query and write the resultset to a csv
	 * file.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {

		Properties properties = new Properties();
		try {
			properties.load(new FileReader(args[0]));
		} catch (FileNotFoundException e1) {
			LOGGER.severe("Couldn't find the properties file");
			e1.printStackTrace();
		} catch (IOException e1) {
			LOGGER.severe("An io exception occured while loading the properties file");
			e1.printStackTrace();
		}
		DBConnection dbConnection = new DBConnection();
		Connection connection = null;
		Statement statement = null;
		ResultSet set = null;

		try {
			connection = dbConnection.initConnection(properties.getProperty("db_driver"),
					properties.getProperty("db_url"), properties.getProperty("db_username"),
					properties.getProperty("db_password"));
		} catch (SQLException e) {
			LOGGER.severe("An error occured while initiating the connection");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			LOGGER.severe("Couldn't find the driver class in the included denepdancies");
			e.printStackTrace();
		}
		try {
			statement = dbConnection.createStatement(connection);
		} catch (SQLException e) {
			LOGGER.severe("An error occured while creating the statement");
			e.printStackTrace();
		}
		try {
			set = dbConnection.executeQuery(statement, "Select * from authentication");
		} catch (SQLException e) {
			LOGGER.severe("An error occured while executing the query");
			e.printStackTrace();
		}

		CSVWriter writer = new CSVWriter();
		writer.setFilePath("export//export.csv");
		writer.setSeparator(",");
		try {
			writer.writeAll(set);
		} catch (FileNotFoundException e) {
			LOGGER.severe("An error occured with the file you have selected to write to");
			e.printStackTrace();
		} catch (SQLException e) {
			LOGGER.severe("An error occured while trying to write to the file");
			e.printStackTrace();
		}

	}

}
