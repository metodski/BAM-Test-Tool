package com.sirma.itt.bam.testtool.matcher;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.junit.Test;

/**
 * Testing the {@link DBConnection} class.
 * 
 * @author N.Velkov
 */
public class ConnectionTest {

	/**
	 * A wrong driver is found in the properties file.
	 * 
	 * @throws FileNotFoundException
	 *             if the file doesn't exist
	 * @throws ClassNotFoundException
	 *             if the driver class doesnt exist
	 * @throws SQLException
	 *             if the url/username/password are wrong
	 */
	@Test(expected = ClassNotFoundException.class)
	public void testPropertiesWrongDriver() throws FileNotFoundException, ClassNotFoundException,
			SQLException {
		DBConnection connection = new DBConnection();

		connection.initConnection("asd", "asd", "asd", "asd");

	}

	/**
	 * A wrong database url is provided.
	 * 
	 * @throws FileNotFoundException
	 *             if the file doesn't exist
	 * @throws ClassNotFoundException
	 *             if the driver class doesnt exist
	 * @throws SQLException
	 *             if the url/username/password are wrong
	 */
	@Test(expected = SQLException.class)
	public void testConnectionWrongURL() throws FileNotFoundException, ClassNotFoundException,
			SQLException {
		DBConnection connection = new DBConnection();

		connection.initConnection("org.postgresql.Driver", "wrong", "wrong", "wrong");
	}

	/**
	 * A wrong username/password is provided.
	 * 
	 * @throws FileNotFoundException
	 *             if the file doesn't exist
	 * @throws ClassNotFoundException
	 *             if the driver class doesnt exist
	 * @throws SQLException
	 *             if the url/username/password are wrong
	 */
	@Test(expected = SQLException.class)
	public void testConnectionWrongCredentials() throws FileNotFoundException,
			ClassNotFoundException, SQLException {
		DBConnection connection = new DBConnection();
		connection.initConnection("org.postgresql.Driver",
				"jdbc:postgresql://10.131.2.223:5432/bam", "wrong", "wrong");
	}

	/**
	 * The connection is succesfully established.
	 * 
	 * @throws FileNotFoundException
	 *             if the file doesn't exist
	 * @throws ClassNotFoundException
	 *             if the driver class doesnt exist
	 * @throws SQLException
	 *             if the url/username/password are wrong
	 */
	@Test
	public void testConnectionSuccess() throws FileNotFoundException, ClassNotFoundException,
			SQLException {
		DBConnection connection = new DBConnection();
		assertTrue(connection.initConnection("org.postgresql.Driver",
				"jdbc:postgresql://10.131.2.223:5432/bam", "postgres", "admin") != null);
	}

	/**
	 * A statement is succesfully created from the established connection.
	 * 
	 * @throws FileNotFoundException
	 *             if the file doesn't exist
	 * @throws ClassNotFoundException
	 *             if the driver class doesnt exist
	 * @throws SQLException
	 *             if the url/username/password are wrong
	 */
	@Test
	public void testStatementSuccess() throws FileNotFoundException, ClassNotFoundException,
			SQLException {
		DBConnection connection = new DBConnection();
		assertTrue(connection.initConnection("org.postgresql.Driver",
				"jdbc:postgresql://10.131.2.223:5432/bam", "postgres", "admin") != null);
	}

	/**
	 * We are trying to send a faulty query to the database.
	 * 
	 * @throws FileNotFoundException
	 *             if the file doesn't exist
	 * @throws ClassNotFoundException
	 *             if the driver class doesnt exist
	 * @throws SQLException
	 *             if the url/username/password are wrong
	 */
	@Test(expected = SQLException.class)
	public void testWrongQuery() throws FileNotFoundException, ClassNotFoundException, SQLException {
		DBConnection connection = new DBConnection();
		connection.createStatement(
				connection.initConnection("org.postgresql.Driver",
						"jdbc:postgresql://10.131.2.223:5432/bam", "postgres", "admin"))
				.executeQuery("Select *asdasd from nowhere");
	}

	/**
	 * Query succesfully executed.
	 * 
	 * @throws FileNotFoundException
	 *             if the file doesn't exist
	 * @throws ClassNotFoundException
	 *             if the driver class doesnt exist
	 * @throws SQLException
	 *             if the url/username/password are wrong
	 */
	@Test
	public void testQuerySuccess() throws FileNotFoundException, ClassNotFoundException,
			SQLException {
		DBConnection connection = new DBConnection();
		assertTrue(connection.createStatement(
				connection.initConnection("org.postgresql.Driver",
						"jdbc:postgresql://10.131.2.223:5432/bam", "postgres", "admin"))
				.executeQuery("Select * from authentication") != null);
	}

}
