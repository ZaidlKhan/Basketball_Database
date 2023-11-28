
package ui;

import database.DatabaseConnectionHandler;
import model.*;
import model.Team;
import model.TeamMember;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TeamInfo extends JFrame {
    public TeamInfo(DatabaseConnectionHandler dbHandler) {

        setSize(400, 550);
        setTitle("TEAMS DATABASE");
        setLocationRelativeTo(null);

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new BorderLayout());
        JButton backButton = new JButton("Back");
        backButtonPanel.add(backButton, BorderLayout.EAST);

        backButton.addActionListener(e -> {
            this.dispose();
        });

        getContentPane().add(backButtonPanel, BorderLayout.NORTH);

        List<Team> teams = dbHandler.getAllTeams();
        DefaultListModel<String> teamListModel = new DefaultListModel<>();
        for (Team team : teams) {
            teamListModel.addElement(team.getName());
        }

        if (teamListModel.isEmpty()) {
            JLabel noTeamsLabel = new JLabel("NO TEAMS");
            noTeamsLabel.setHorizontalAlignment(JLabel.CENTER);
            getContentPane().add(noTeamsLabel, BorderLayout.CENTER);
        } else {
            JList<String> teamList = new JList<>(teamListModel);
            teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JScrollPane scrollPane = new JScrollPane(teamList);
            getContentPane().add(scrollPane, BorderLayout.CENTER);

            teamList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    JList<String> list = (JList<String>) evt.getSource();
                    if (evt.getClickCount() == 2) {
                        int index = list.locationToIndex(evt.getPoint());
                        displayTeamInfo(teams.get(index), dbHandler);
                    }
                }
            });
        }

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void displayTeamInfo(Team team, DatabaseConnectionHandler dbHandler) {
        StringBuilder teamInfo = new StringBuilder();

        teamInfo.append("Team Name: ").append(team.getName()).append("\n");
        teamInfo.append("Team ID: ").append(team.getTeam_id()).append("\n");
        teamInfo.append("Arena: ").append(team.getAreana()).append("\n");

        teamInfo.append("\n");
        List<String> names = dbHandler.getTeamGames(team.getName());
        if (!names.isEmpty()) {
            teamInfo.append("Games: \n");
            for (String name: names) {
                teamInfo.append(" - ").append(name).append("\n");
            }
        } else {
            teamInfo.append("No games scheduled\n");
        }

        teamInfo.append("\n");
        List<TeamMember> members = dbHandler.getAllTeamMembers(team.getTeam_id());
        if (!members.isEmpty()) {
            teamInfo.append("Team Members: \n");
            for (TeamMember member : members) {
                teamInfo.append(" - ").append(member.getName()).append("\n");
            }
        } else {
            teamInfo.append("No team members\n");
        }

        teamInfo.append("\n");
        Owner owner = dbHandler.getOwner(team.getTeam_id());
        teamInfo.append("Owner: ").append(owner.getName()).append("\n");
        teamInfo.append(" - ").append("Age: " + owner.getAge()).append("\n");
        teamInfo.append(" - ").append("Net Worth: " + owner.getNet_worth()).append("\n");


        teamInfo.append("\n");
        List<Sponsor> sponsors = dbHandler.getAllSponsors(team.getName());
        if (!sponsors.isEmpty()) {
            teamInfo.append("Sponsors: \n");
            for (Sponsor sponsor : sponsors) {
                teamInfo.append(" - ").append(sponsor.getName()).append("\n");
                teamInfo.append("--").append(sponsor.getContributions()).append("\n").append("\n");
            }
        } else {
            teamInfo.append("No sponsors\n");
        }


        JOptionPane.showMessageDialog(this, teamInfo.toString());
        }
    }



