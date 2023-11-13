package ui;

import model.*;


public class Main {

    public static void main(String[] args) {
        Season season = new Season(2023, "2023/10/24", "2024/04/18");

        Team toronto_raptors = new Team("Toronto Raptors", "Toronto", "Scotiabank Arena", 18,
                new Owner("Billy Johnson", 10000000, "1987/07/19"));
        Team houston_rockets = new Team("Houston Rockets", "Houston", "Staples Center", 12,
                new Owner("John Doe", 30000000, "1967/09/05"));

        TeamMember jason_tom = new Player(1, "Jason Tom", toronto_raptors, 22, "2022/09/12",
                "2025/04/18", 2000000, "F");
        TeamMember john_smith = new Player(2, "John Smith", toronto_raptors, 26, "2023/01/15",
                "2026/07/22", 3600000, "G");
        TeamMember alex_johnson = new Player(3, "Alex Johnson", toronto_raptors, 28, "2022/11/10",
                "2024/12/30", 4100000, "C");
        TeamMember kyle_brown = new Coach(7, "Kyle Brown", toronto_raptors, 21, "2023/07/01",
                "2028/06/30", 2400000, "Head Coach");
        TeamMember liam_davis = new Coach(8, "Liam Davis", toronto_raptors, 27, "2022/08/15",
                "2025/05/20", 3000000, "Assistant Coach");

        TeamMember daniel_kim = new Player(4, "Daniel Kim", houston_rockets, 23, "2023/03/21",
                "2027/06/19", 1750000, "F");
        TeamMember luis_rodriguez = new Player(5, "Luis Rodriguez", houston_rockets, 30, "2022/10/05",
                "2025/09/14", 2950000, "G");
        TeamMember raj_singh = new Player(6, "Raj Singh", houston_rockets, 24, "2023/02/28",
                "2026/08/03", 3300000, "C");
        TeamMember ethan_martinez = new Coach(9, "Ethan Martinez", houston_rockets, 29, "2023/10/12",
                "2026/04/25", 3800000, "Head Coach");
        TeamMember noah_thompson = new Coach(10, "Noah Thompson", houston_rockets, 26, "2022/12/05",
                "2026/11/30", 3250000, "Assistant Coach");


        toronto_raptors.addTeam_member(jason_tom);
        toronto_raptors.addTeam_member(john_smith);
        toronto_raptors.addTeam_member(alex_johnson);
        toronto_raptors.addTeam_member(kyle_brown);
        toronto_raptors.addTeam_member(liam_davis);

        houston_rockets.addTeam_member(daniel_kim);
        houston_rockets.addTeam_member(luis_rodriguez);
        houston_rockets.addTeam_member(raj_singh);
        houston_rockets.addTeam_member(ethan_martinez);
        houston_rockets.addTeam_member(noah_thompson);

        Referee referee = new Referee(901, "Joseph Thompsen", 5);
        StatSheet statSheet = new StatSheet(89032, 101, 98, 34, 38,
                27, 31, 6, 8);

        Sponsor rbc = new Sponsor("RBC", 102000);
        Sponsor mcdonalds = new Sponsor("Mcdonalds", 2000000);
        Sponsor nike = new Sponsor("Nike", 2100000);

        toronto_raptors.add_sponsor(rbc);
        toronto_raptors.add_sponsor(nike);
        houston_rockets.add_sponsor(mcdonalds);

        Game game1 = new Game(10091, "2023/10/28", toronto_raptors, houston_rockets,
                referee, statSheet);

        toronto_raptors.add_game(game1);
        houston_rockets.add_game(game1);
        season.add_games(game1);

        new MainMenu(season);
    }
}