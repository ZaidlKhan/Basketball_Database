package ui;

import database.DatabaseConnectionHandler;
import model.Owner;
import model.Sponsor;
import model.Team;
import model.TeamMember;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TeamInfoWindow extends JFrame {

    public TeamInfoWindow(Team team, DatabaseConnectionHandler dbHandler) {
        setTitle("Team Information");
        setSize(705, 525);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea teamInfoTextArea = new JTextArea();
        teamInfoTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        teamInfoTextArea.setEditable(false);
        teamInfoTextArea.setBounds(240, 0, 225, 525);
        teamInfoTextArea.setForeground(Color.white);
        teamInfoTextArea.setBackground(new Color(148, 119, 59));

        JTextArea teamInfoTextArea2 = new JTextArea();
        teamInfoTextArea2.setFont(new Font("Arial", Font.PLAIN, 16));
        teamInfoTextArea2.setEditable(false);
        teamInfoTextArea2.setBounds(0, 0, 225, 525);
        teamInfoTextArea2.setForeground(Color.white);
        teamInfoTextArea2.setBackground(new Color(148, 119, 59));

        JTextArea teamInfoTextArea3 = new JTextArea();
        teamInfoTextArea3.setFont(new Font("Arial", Font.PLAIN, 16));
        teamInfoTextArea3.setEditable(false);
        teamInfoTextArea3.setBounds(480, 0, 225, 525);
        teamInfoTextArea3.setForeground(Color.white);
        teamInfoTextArea3.setBackground(new Color(148, 119, 59));

        Font font = new Font("Arial", Font.BOLD, 16);
        teamInfoTextArea.setFont(font);
        teamInfoTextArea2.setFont(font);
        teamInfoTextArea3.setFont(font);

        StringBuilder teamInfo = new StringBuilder();
        StringBuilder teamInfo2 = new StringBuilder();
        StringBuilder teamInfo3 = new StringBuilder();

        teamInfo.append("\n");
        teamInfo.append("     Team Name ").append("\n          - ").append(team.getName()).append("\n\n\n");
        teamInfo.append("     Team ID ").append("\n           - ").append(team.getTeam_id()).append("\n\n\n");
        teamInfo.append("     Arena ").append("\n           - ").append(team.getAreana()).append("\n\n\n");

        Owner owner = dbHandler.getOwner(team.getTeam_id());
        teamInfo.append("     Owner: ").append("\n          - ").append(owner.getName()).append("\n");
        teamInfo.append("           - ").append("Age: ").append(owner.getAge()).append("\n");
        teamInfo.append("           - ").append("Net Worth: ").append(owner.getNet_worth()).append("\n");
        teamInfo.append("\n\n");

        List<Sponsor> sponsors = dbHandler.getAllSponsors(team.getName());
        if (!sponsors.isEmpty()) {
            teamInfo.append("     Sponsors: \n");
            for (Sponsor sponsor : sponsors) {
                teamInfo.append("           - ").append(sponsor.getName()).append("\n");
                teamInfo.append("           - ").append(sponsor.getContributions()).append("\n").append("\n");
            }
        } else {
            teamInfo.append("No sponsors\n");
        }

        // Display team information on the left side
        teamInfoTextArea.setText(teamInfo.toString());
        getContentPane().add(teamInfoTextArea);

        teamInfo2.append("\n");
        List<TeamMember> members = dbHandler.getAllTeamMembers(team.getTeam_id());
        if (!members.isEmpty()) {
            teamInfo2.append("              Team Members \n\n\n");
            for (TeamMember member : members) {
                teamInfo2.append("   -   ").append(member.getName()).append("\n\n");
            }
        } else {
            teamInfo2.append("No team members\n");
        }

        // Display team members on the right side
        teamInfoTextArea2.setText(teamInfo2.toString());
        getContentPane().add(teamInfoTextArea2);

        teamInfo3.append("\n");
        List<String> names = dbHandler.getTeamGames(team.getName());
        if (!names.isEmpty()) {
            teamInfo3.append("                   Games \n\n\n");
            for (String name : names) {
                teamInfo3.append("   -   ").append(name.trim()).append("\n\n");
            }
        } else {
            teamInfo3.append("No games scheduled\n");
        }

        teamInfoTextArea3.setText(teamInfo3.toString());
        getContentPane().add(teamInfoTextArea3);






        setLocationRelativeTo(null);
        setLayout(null); // Set layout to null for absolute positioning
        setVisible(true);
    }

}
