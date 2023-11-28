package model;

public class StatSheet {
    private final int statsheet_id;
    private final int home_points;
    private final int away_points;
    private final int home_rebounds;
    private final int away_rebounds;
    private final int home_assits;
    private final int away_assits;
    private final int home_steals;
    private final int away_steals;

    public StatSheet(int statsheet_id, int home_points, int away_points,int home_steals, int away_steals, int home_assits, int away_assits, int home_rebounds, int away_rebounds) {
        this.statsheet_id = statsheet_id;
        this.home_points = home_points;
        this.away_points = away_points;
        this.home_rebounds = home_rebounds;
        this.away_rebounds = away_rebounds;
        this.home_assits = home_assits;
        this.away_assits = away_assits;
        this.home_steals = home_steals;
        this.away_steals = away_steals;
    }

    public int getStatsheet_id() {
        return statsheet_id;
    }

    public int getHome_points() {
        return home_points;
    }

    public int getAway_points() {
        return away_points;
    }

    public int getHome_rebounds() {
        return home_rebounds;
    }

    public int getAway_rebounds() {
        return away_rebounds;
    }

    public int getHome_assits() {
        return home_assits;
    }

    public int getAway_assits() {
        return away_assits;
    }

    public int getHome_steals() {
        return home_steals;
    }

    public int getAway_steals() {
        return away_steals;
    }
}