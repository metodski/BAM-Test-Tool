package com.sirma.itt.bam.testtool.matcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Class that establishes the database connection.
 * 
 * @author N.Velkov
 */
public class DBConnection {

	private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

	
	/**
	 * Initialize the database connection.
	 * @param databaseDriver the database driver
	 * @param databaseURL url of the database
	 * @param databaseUserName username of a valid account
	 * @param databasePassword password of a valid account
	 * @return the established connection, otherwise null
	 * @throws SQLException if the connection couldnt be established
	 * @throws ClassNotFoundException if the driver class couldnt be found
	 */
	public Connection initConnection(String databaseDriver, String databaseURL, String databaseUserName, String databasePassword) throws SQLException,
			ClassNotFoundException {
		try {
			Class.forName(databaseDriver);
		} catch (ClassNotFoundException e2) {
			LOGGER.severe("Couldn't find the driver for the database connection " + databaseDriver);
			throw new ClassNotFoundException(
					"Couldn't find the driver for the database connection " + databaseDriver
							+ e2.getMessage());
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(databaseURL,
					databaseUserName, databasePassword);
		} catch (SQLException e) {
			LOGGER.severe("Couldn't establish connection to database "
					+ databaseURL);
			throw new SQLException("Couldn't establish connection to database "
					+ databaseURL + e.getMessage());
		}
		return connection;
	}

	/**
	 * Create the statement.
	 * 
	 * @param connection
	 *            the connection from which to create the statement
	 * @return the created statement
	 * @throws SQLException
	 *             if there was something wrong with the connection to the database
	 */
	public Statement createStatement(Connection connection) throws SQLException {
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			LOGGER.severe("Couldn't create statement");
			throw new SQLException("Couldn't create statement" + e.getMessage());
		}
		return statement;
	}

	/**
	 * Execute query on the given statement.
	 * 
	 * @param statement
	 *            the statement to execute the query on
	 * @param query
	 *            the query to execute
	 * @return the result set from the executed query
	 * @throws SQLException
	 *             if there was something wrong with the connection to the database
	 */
	public ResultSet executeQuery(Statement statement, String query) throws SQLException {
		ResultSet set = null;
		try {
			set = statement.executeQuery(query);
		} catch (SQLException e) {
			LOGGER.severe("Couldn't execute query " + query);
			throw new SQLException("Couldn't execute query " + query + e.getMessage());
		}
		return set;
	}


}
