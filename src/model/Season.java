package model;

import java.util.ArrayList;
import java.util.Date;

public class Season {
    private Date year;          //key
    private Date start_date;
    private Date end_date;
    private ArrayList<Game> games;

    public Season(Date year, Date start_date, Date end_date) {
        this.year = year;
        this.start_date = start_date;
        this.end_date = end_date;
        this.games = new ArrayList<>();
    }

    public Date getYear() {
        return year;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void add_games(Game game){
        this.games.add(game);
    }
}