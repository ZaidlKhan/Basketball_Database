package model;

public class Owner {
    private String name;
    private int net_worth;
    private int age;

    public Owner(String name, int age, int net_worth) {
        this.name = name;
        this.net_worth = net_worth;
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public int getNet_worth(){
        return net_worth;
    }

    public int getAge(){
        return age;
    }
}