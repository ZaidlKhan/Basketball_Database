package database;

import model.Season;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {

	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;

	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}

			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	public List<Season> getAllSeasons() {
		List<Season> seasons = new ArrayList<>();

		try {
			String query = "SELECT * FROM season";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				 ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					int year = resultSet.getInt("year");
					String startDate = resultSet.getString("start_date");
					String endDate = resultSet.getString("end_date");


					Season season = new Season(year, startDate, endDate);
					seasons.add(season);
				}
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return seasons;
	}

}