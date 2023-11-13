package model;

import java.util.ArrayList;

public class Team {
    private final String name;
    private final String city;
    private final String areana;
    private final int team_id;
    private final Owner owner;
    private final ArrayList<Sponsor> sponsers;
    private final ArrayList<TeamMember> team_members;
    private final ArrayList<Game> games;

    Team(String name, String city, String arena, int team_id, Owner owner) {
        this.name = name;
        this.city = city;
        this.areana = arena;
        this.team_id = team_id;
        this.owner = owner;
        this.games = new ArrayList<>();
        this.team_members = new ArrayList<>();
        this.sponsers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getAreana() {
        return areana;
    }

    public int getTeam_id() {
        return team_id;
    }

    public Owner getOwner() {
        return owner;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public ArrayList<TeamMember> getTeam_members() {
        return team_members;
    }

    public ArrayList<Sponsor> getSponsers() {
        return sponsers;
    }
}