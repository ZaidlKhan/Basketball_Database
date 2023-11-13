package ui;

import model.Season;

import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        Season season = new Season(new Date(2023), new Date(2023, Calendar.OCTOBER, 23),
                new Date(2024, Calendar.APRIL, 18));
        new MainMenu(season);
    }
}