package model;

public class TeamMember {
    private final int player_id;
    private final String name;
    private Team team;
    private int age;
    private String start_date;
    private String end_date;
    private int salary;

    TeamMember(int player_id, String name, Team team, int age, String start_date,
               String end_date, int salary) {
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

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public int getSalary() {
        return salary;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setStartDate(String startDate) {
        this.start_date = startDate;
    }

    public void setEndDate(String end_date) {
        this.end_date = end_date;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}