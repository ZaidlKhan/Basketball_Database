package model;

public class Game {
    private final String game_date;
    private final int home_team;
    private final int away_team;
    private final String score;
    private final int ssid;
    private final int year;
    private final int rid;
    private final String arena;

    public Game(String game_date, int home_team, int away_team, String score, int ssid, int year, int rid, String arena) {
        this.game_date = game_date;
        this.home_team = home_team;
        this.away_team = away_team;
        this.score = score;
        this.ssid = ssid;
        this.year = year;
        this.rid = rid;
        this.arena = arena;
    }

    // Getter methods

    public String getGameDate() {
        return game_date;
    }

    public int getHomeTeam() {
        return home_team;
    }

    public int getAwayTeam() {
        return away_team;
    }

    public String getScore() {
        return score;
    }

    public int getSsid() {
        return ssid;
    }

    public int getYear() {
        return year;
    }

    public int getRid() {
        return rid;
    }

    public String getArena() {
        return arena;
    }
}
