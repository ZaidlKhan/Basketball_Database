package model;

public class TeamMember {
    private final int player_id;
    private final String name;
    private final Team team;
    private final int age;
    private final int start_date;
    private final int end_date;
    private final int salary;

    TeamMember(int player_id, String name, Team team, int age, int start_date,
               int end_date, int salary) {
        this.player_id = player_id;
        this.name = name;
        this.team = team;
        this.age = age;
        this.start_date = start_date;
        this.end_date = end_date;
        this.salary = salary;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public int getAge() {
        return age;
    }

    public int getStart_date() {
        return start_date;
    }

    public int getEnd_date() {
        return end_date;
    }

    public int getSalary() {
        return salary;
    }

}