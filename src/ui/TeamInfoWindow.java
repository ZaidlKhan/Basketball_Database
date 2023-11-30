package ui;

import database.DatabaseConnectionHandler;
import model.Team;
import model.TeamMember;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TeamInfoWindow extends JFrame {

    public TeamInfoWindow(Team team, DatabaseConnectionHandler dbHandler) {
        setTitle("Team Information");
        setSize(465, 525);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Left side
        JTextArea teamInfoTextArea = new JTextArea();
        teamInfoTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        //teamInfoTextArea.setOpaque(false);
        teamInfoTextArea.setEditable(false);
        teamInfoTextArea.setBounds(240, 0, 225, 525);
        teamInfoTextArea.setForeground(Color.white);
        teamInfoTextArea.setBackground(new Color(148, 119, 59));

        // Right side
        JTextArea teamInfoTextArea2 = new JTextArea();
        teamInfoTextArea2.setFont(new Font("Arial", Font.PLAIN, 16));
        teamInfoTextArea2.setEditable(false);
        teamInfoTextArea2.setBounds(0, 0, 225, 525);
        teamInfoTextArea2.setForeground(Color.white);
        teamInfoTextArea2.setBackground(new Color(148, 119, 59));

        Font font = new Font("Arial", Font.BOLD, 16);
        teamInfoTextArea.setFont(font);
        teamInfoTextArea2.setFont(font);

        StringBuilder teamInfo = new StringBuilder();
        StringBuilder teamInfo2 = new StringBuilder();

        teamInfo.append("\n");
        teamInfo.append("     Team Name ").append("\n          - ").append(team.getName()).append("\n\n\n");
        teamInfo.append("     Team ID ").append("\n           - ").append(team.getTeam_id()).append("\n\n\n");
        teamInfo.append("     Arena ").append("\n           - ").append(team.getAreana()).append("\n\n");

        teamInfo.append("\n");
        List<String> names = dbHandler.getTeamGames(team.getName());
        if (!names.isEmpty()) {
            teamInfo.append("                   Games \n\n\n");
            for (String name : names) {
                teamInfo.append("   -   ").append(name.trim()).append("\n\n");
            }
        } else {
            teamInfo.append("No games scheduled\n");
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

        setLocationRelativeTo(null);
        setLayout(null); // Set layout to null for absolute positioning
        setVisible(true);
    }

}
