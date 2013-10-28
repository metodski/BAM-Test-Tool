package com.sirma.itt.bam.testtool.matcher;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * The writer that creates and writes to the csv file .
 * @author N.Velkov
 */
public class CSVWriter {
	private String separator;
	private String filePath;
	private static final Logger LOGGER = Logger.getLogger(CSVWriter.class.getName());

	/**
	 * Write all the rows of the result set to the csv file with the given separator.
	 * @param set the result set from which to get the data
	 * @throws FileNotFoundException if the set file path is not found
	 * @throws SQLException if there was something wrong with the database connection
	 */
	public void writeAll(ResultSet set) throws FileNotFoundException, SQLException {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filePath, "UTF-8");
		} catch (FileNotFoundException e) {
			LOGGER.severe("Couldn't find the file you are trying to write to" + filePath);
			throw new FileNotFoundException("Couldn't find the file you are trying to write to -" + filePath + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			LOGGER.severe("Unsupported encoding");
			e.printStackTrace();
		}
		writer.println("sep=" + separator);
		try {
			for (int i = 1; i <= set.getMetaData().getColumnCount(); i++)
				if (i != 1)
					writer.print(separator + set.getMetaData().getColumnName(i));
				else
					writer.print(set.getMetaData().getColumnName(i));
			writer.println();
			while (set.next()) {
				for (int i = 1; i <= set.getMetaData().getColumnCount(); i++) {
					String element = null;
					if(set.getString(i).contains(separator))
						element = "\"" + set.getString(i) + "\"";
					else element = set.getString(i);
					if (i != 1)
						writer.print(separator + element);
					else
						writer.print(element);
				}
				writer.println();
			}
			writer.close();
			set.close();
		} catch (SQLException e) {
			LOGGER.severe("An SQL exception occured while writing to file");
			throw new SQLException("An SQL exception occured while writing to file " + e.getMessage());
		}


	}

	/**
	 * Getter method for separator.
	 * 
	 * @return the separator
	 */
	public String getSeparator() {
		return separator;
	}

	/**
	 * Setter method for separator.
	 * 
	 * @param separator
	 *            the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/**
	 * Getter method for filePath.
	 * 
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Setter method for filePath.
	 * 
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
