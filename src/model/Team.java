package model;

public class Team {
    private final String name;
    private final String arena;
    private final int team_id;

    public Team(int team_id, String name, String arena) {
        this.name = name;
        this.arena = arena;
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public String getAreana() {
        return arena;
    }

    public int getTeam_id() {
        return team_id;
    }

}