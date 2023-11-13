package model;

public class Owner {
    private String name;
    private int net_worth;
    private int dob;

    public Owner(String name, int net_worth, int dob) {
        this.name = name;
        this.net_worth = net_worth;
        this.dob = dob;
    }

    public String getName(){
        return name;
    }

    public int getNet_worth(){
        return net_worth;
    }

    public int getDob(){
        return dob;
    }
}