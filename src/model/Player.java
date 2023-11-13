package model;

public class Player extends TeamMember {
    private final String position;

    public Player(int player_id, String name, Team team, int age, String start_date,
                  String end_date, int salary, String position) {
        super(player_id, name, team, age, start_date, end_date, salary);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}