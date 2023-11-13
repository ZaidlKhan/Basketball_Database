import java.util.ArrayList;

public class Season {
    private int year;          //key
    private int start_date;
    private int end_date;
    private ArrayList<Game> games;

    Season(int year, int start_date, int end_date) {
        this.year = year;
        this.start_date = start_date;
        this.end_date = end_date;
        this.games = new ArrayList<>();
    }

    public int getYear() {
        return year;
    }

    public int getStart_date() {
        return start_date;
    }

    public int getEnd_date() {
        return end_date;
    }

    public Game[] getGames() {
        return games;
    }

    public void add_games(Game game){
        this.games.add(game);
    }
}