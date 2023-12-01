package database;

import model.*;
import util.PrintablePreparedStatement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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


        String query = "SELECT t1.name as name1,t2.name as name2 " +
                "FROM game g, team t1, team t2 " +
                "WHERE g.HOME_TID = t1.TID AND g.AWAY_TID = t2.TID AND (t1.name = ? or t2.name = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            {

                while (resultSet.next()) {
                    String name1 = resultSet.getString("name1");
                    String name2 = resultSet.getString("name2");

                    String str = name1.trim() + " VS " + name2.trim();
                    gameNames.add(str);
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return gameNames;
    }

    public List<Sponsor> getAllSponsors(String name) {
        List<Sponsor> sponsors = new ArrayList<>();

        try {
            String query = "SELECT s.name as sponsor, s.CONTRIBUTION " +
                    "FROM sponsors ss " +
                    "JOIN sponsor s ON s.name = ss.sname " +
                    "JOIN team t ON t.TID = ss.TID " +
                    "WHERE t.name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String sponsor = resultSet.getString("sponsor");
                        int contribution = resultSet.getInt("contribution");
                        Sponsor sponsorObject = new Sponsor(sponsor, contribution);
                        sponsors.add(sponsorObject);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return sponsors;
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

                    Game game = new Game(gameDate, home_tid, away_tid, score, ssid, year, rid, arena);
                    games.add(game);
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return games;
    }

    public int getMaxTeamID() {
        int max = 0;

        try {
            String query = "SELECT MAX(tmid) as max_id from TeamMember";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    max = resultSet.getInt("max_id");
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return max;
    }

    public Player getPlayerByID(int id) {
        Player player = null;
        try {
            String query = "select position from player where pid = " + id;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String pos = resultSet.getString("position");

                    TeamMember mem = getMemberByID(id);
                    player = new Player(mem.getPlayer_id(), mem.getName(), mem.getTeam(), mem.getAge(), mem.getStart_date(), mem.getEnd_date(), mem.getSalary(), pos);
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return player;
    }

    public void deleteTeamMember(int memberID) {
        try {
            String query = "DELETE FROM TeamMember WHERE tmid = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, memberID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " TeamMember with tmid " + memberID + " does not exist!");
            }
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public int updateMember(TeamMember member, int teamID, int age, int salary, String start, String end) {
        int x = 0;
        try {
            String query = "Update TEAMMEMBER set tid = ?, age = ?, salary = ?, start_date = ?, END_DATE = ? where  tmid = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, teamID);
            ps.setInt(2, age);
            ps.setInt(3, salary);
            ps.setDate(4, java.sql.Date.valueOf(start));
            ps.setDate(5, java.sql.Date.valueOf(end));
            ps.setInt(6, member.getPlayer_id());

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " TeamMember with tmid " + member.getPlayer_id() + " does not exist!");
                return x;
            }
            connection.commit();
            ps.close();
            x = 1;
            return x;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return x;
    }

    public TeamMember getMemberByID(int id) {
        TeamMember member = null;
        try {
            String query = "select tm.*,t.name as teamname,t.arena from TEAMMEMBER tm, Team t where t.tid = tm.tid and tm.tmid = " + id;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int tmid = resultSet.getInt("tmid");
                    String memberName = resultSet.getString("name");
                    int tid = resultSet.getInt("tid");
                    String start_date = resultSet.getString("start_date");
                    String end_date = resultSet.getString("end_date");
                    int salary = resultSet.getInt("salary");
                    int age = resultSet.getInt("age");
                    String teamName = resultSet.getString("teamname");
                    String arena = resultSet.getString("arena");

                    Team team = new Team(tid, teamName, arena);
                    member = new TeamMember(tmid, memberName, team, age, start_date, end_date, salary);
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return member;
    }

    public List<TeamMember> getAllTeamMembers(int id) {
        List<TeamMember> teamMembers = new ArrayList<>();

        try {
            String query = "select tm.*, t.name as tname,t.arena from TeamMember tm, Team t where t.TID = " + id + " and t.tid = tm.tid";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int tmid = resultSet.getInt("tmid");
                    String memberName = resultSet.getString("name");
                    int tid = resultSet.getInt("tid");
                    String start_date = resultSet.getString("start_date");
                    String end_date = resultSet.getString("end_date");
                    int salary = resultSet.getInt("salary");
                    int age = resultSet.getInt("age");
                    String teamName = resultSet.getString("tname");
                    String arena = resultSet.getString("arena");

                    Team team = new Team(tid, teamName, arena);
                    TeamMember member = new TeamMember(tmid, memberName, team, age, start_date, end_date, salary);
                    teamMembers.add(member);
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return teamMembers;
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

                    Team team = new Team(tid, name, arena);
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

                    ref = new Referee(rid, name, exp);
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return ref;
    }

    // Projection
    public StatSheet getHomeStats(int ssid) {
        StatSheet stats = null;
        try {
            String query = "SELECT ssid, home_points, home_steals, home_assists, home_rebounds from Statsheet where ssid = " + ssid;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ssid2 = resultSet.getInt("ssid");
                    int homepoints = resultSet.getInt("home_points");
                    int homesteals = resultSet.getInt("home_steals");
                    int homeassists = resultSet.getInt("home_assists");
                    int homerebounds = resultSet.getInt("home_rebounds");

                    stats = new StatSheet(ssid2, homepoints, 0, homesteals, 0, homeassists, 0, homerebounds, 0);
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return stats;
    }

    public StatSheet getAwayStats(int ssid) {
        StatSheet stats = null;
        try {
            String query = "SELECT ssid, away_points, away_steals, away_assists, away_rebounds from Statsheet where ssid = " + ssid;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ssid2 = resultSet.getInt("ssid");
                    int awaypoints = resultSet.getInt("away_points");
                    int awaysteals = resultSet.getInt("away_steals");
                    int awayassists = resultSet.getInt("away_assists");
                    int awayrebounds = resultSet.getInt("away_rebounds");

                    stats = new StatSheet(ssid2, 0, awaypoints, 0, awaysteals, 0, awayassists, 0, awayrebounds);
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return stats;
    }

    public Owner getOwner(int id) {
        Owner owner = null;
        try {
            String query = "select os.oname as ownername,o.age,o.NET_WORTH from owns os, owner o where os.oname = o.name and os.tid = " + id;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String oname = resultSet.getString("ownername");
                    int age = resultSet.getInt("age");
                    int netw = resultSet.getInt("net_worth");
                    owner = new Owner(oname, age, netw);
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return owner;

    }

    public void insertTeamMember(int tmid, String name, int tid, Date start, Date end, int salary, int age) {
        try {
            String query = "INSERT INTO TeamMember VALUES (?,?,?,?,?,?,?)";
            PrintablePreparedStatement preparedStatement = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            preparedStatement.setInt(1, tmid);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, tid);
            preparedStatement.setDate(4, start);
            preparedStatement.setDate(5, end);
            preparedStatement.setInt(6, salary);
            preparedStatement.setInt(7, age);
            preparedStatement.executeUpdate();
            connection.commit();

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertPlayer(int tmid, String pos) {
        try {
            String query = "INSERT INTO Player VALUES (?,?)";
            PrintablePreparedStatement preparedStatement = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            preparedStatement.setInt(1, tmid);
            preparedStatement.setString(2, pos);
            preparedStatement.executeUpdate();
            connection.commit();

            preparedStatement.close();
            System.out.println("Successfully added to Player with tmid = " + " and position " + pos);
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public int getTotalSalaryForTeam(int teamId) {
        int totalSalary = 0;

        try {
            String query = "SELECT SUM(salary) AS total FROM TeamMember WHERE tid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, teamId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        totalSalary = resultSet.getInt("total");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return totalSalary;
    }

    private void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }


    public void executeSQLScript() {
        String[] sqlScript = readTextFile("src/runScript.txt");
        int count = 0;
        for (String sqlStatement : sqlScript) {
            try {
                PrintablePreparedStatement preparedStatement = new PrintablePreparedStatement(connection.prepareStatement(sqlStatement), sqlStatement, false);
                preparedStatement.executeUpdate();

                connection.commit();
                preparedStatement.close();
                count++;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("SQL script executed successfully.");
        System.out.println("Total executed commands :" + count);


    }

    public static String[] readTextFile(String filePath) {
        List<String> statements = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder currentStatement = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                currentStatement.append(line.trim());
                if (line.trim().endsWith(";")) {
                    statements.add(currentStatement.substring(0, currentStatement.length() - 1).trim());
                    currentStatement = new StringBuilder();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statements.toArray(new String[0]);
    }
}