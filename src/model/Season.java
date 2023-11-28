package model;

import java.util.ArrayList;

public class Season {
    private int year;          //key
    private String start_date;
    private String end_date;
    private ArrayList<Game> games;

    public Season(int year, String start_date, String end_date) {
        this.year = year;
        this.start_date = start_date;
        this.end_date = end_date;
        this.games = new ArrayList<>();
    }

    public int getYear() {
        return year;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String toString() {
        String x = "Year: " + this.getYear();
        return x;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void add_games(Game game){
        this.games.add(game);
    }


}