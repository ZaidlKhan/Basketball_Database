package database;

import model.*;

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
	public List<String> getTeamGames(String name) {
		List<String> gameNames = new ArrayList<>();

		try {
			String query = "select t1.name as name1,t2.name as name2 from game g, team t1, team t2 where g.HOME_TID = t1.TID and g.AWAY_TID = t2.TID and (t1.name = " + " '" + name + "'" +  " or t2.name = " + "'" + name + "' " + ")";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				 ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					String name1 = resultSet.getString("name1");
					String name2 = resultSet.getString("name2");

					String str = name1 + " VS " + name2;
					gameNames.add(str);
				}
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return gameNames;

	}
	public List<Game> getAllGames(int yrr) {
		List<Game> games = new ArrayList<>();

		try {
			String query = "SELECT * FROM game g WHERE g.year = " + yrr;
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				 ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					String gameDate = resultSet.getString("game_date");
					int home_tid = resultSet.getInt("home_tid");
					int away_tid = resultSet.getInt("away_tid");
					String score = resultSet.getString("score");
					int ssid = resultSet.getInt("ssid");
					int year = resultSet.getInt("year");
					int rid = resultSet.getInt("rid");
					String arena = resultSet.getString("arena");

					Game game = new Game(gameDate,home_tid,away_tid,score,ssid,year,rid,arena);
					games.add(game);
				}
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return games;
	}

	public List<Team> getAllTeams() {
		List<Team> teams = new ArrayList<>();

		try {
			String query = "SELECT tid,name,arena FROM team";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				 ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					int tid = resultSet.getInt("tid");
					String name = resultSet.getString("name");
					String arena = resultSet.getString("arena");

					Team team = new Team(tid,name,arena);
					teams.add(team);
				}
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return teams;
	}

	public Referee getReferee(int id) {
		Referee ref = null;
		try {
			String query = "SELECT * from Referee where rid = " + id;
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				 ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int rid = resultSet.getInt("rid");
					String name = resultSet.getString("name");
					int exp = resultSet.getInt("experience_years");

					ref = new Referee(rid,name,exp);
				}
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return ref;
	}

	public StatSheet getStats(int ssid) {
		StatSheet stats = null;
		try {
			String query = "SELECT * from Statsheet where ssid = " + ssid;
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);
				 ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int ssid2 = resultSet.getInt("ssid");
					int homepoints = resultSet.getInt("home_points");
					int awaypoints = resultSet.getInt("away_points");
					int homesteals = resultSet.getInt("home_steals");
					int awaysteals = resultSet.getInt("away_steals");
					int homeassists = resultSet.getInt("home_assists");
					int awayassists = resultSet.getInt("away_assists");
					int homerebounds = resultSet.getInt("home_rebounds");
					int awayrebounds = resultSet.getInt("away_rebounds");


					stats = new StatSheet(ssid2,homepoints,awaypoints,homesteals,awaysteals,homeassists,awayassists,homerebounds,awayrebounds);
				}
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}

		return stats;
	}

}