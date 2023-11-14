package model;

public class Game {
    private final int game_id;
    private final String date;
    private final Team home_team;
    private final Team away_team;
    private final Referee referee;
    private final StatSheet statSheet;

    public Game(int game_id, String date, Team home_team, Team away_team, Referee referee, StatSheet statSheet) {
        this.game_id = game_id;
        this.date = date;
        this.home_team = home_team;
        this.away_team = away_team;
        this.referee = referee;
        this.statSheet = statSheet;
    }

    public int getGame_id() {
        return game_id;
    }

    public String getDate() {
        return date;
    }

    public Team getHome_team() {
        return home_team;
    }

    public Team getAway_team() {
        return away_team;
    }

    public Referee getReferee() {
        return referee;
    }

    public StatSheet getStatSheet() {
        return statSheet;
    }
}