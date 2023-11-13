package model;

public class Coach extends TeamMember {
    private final String role;

    public Coach(int player_id, String name, Team team, int age, String start_date,
                 String end_date, int salary, String role) {
        super(player_id, name, team, age, start_date, end_date, salary);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}