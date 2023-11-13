package model;

public class Player extends TeamMember {
    private final String position;

    Player(int player_id, String name, Team team, int age, int start_date,
           int end_date, int salary, String position) {
        super(player_id, name, team, age, start_date, end_date, salary);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}