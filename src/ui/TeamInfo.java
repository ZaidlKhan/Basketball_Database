package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TeamInfo extends JFrame {
    public TeamInfo(Season season) {
        setSize(400, 550);
        setTitle("TEAMS DATABASE");
        setLocationRelativeTo(null);

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new BorderLayout());
        JButton backButton = new JButton("Back");
        backButtonPanel.add(backButton, BorderLayout.EAST);

        backButton.addActionListener(e -> {
            this.dispose();
            new MainMenu(season);
        });

        getContentPane().add(backButtonPanel, BorderLayout.NORTH);

        DefaultListModel<String> teamListModel = new DefaultListModel<>();
        for (Team team : season.getTeams()) {
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
                        String selectedTeamName = list.getModel().getElementAt(index);
                        Team selectedTeam = findTeamByName(season, selectedTeamName);
                        if (selectedTeam != null) {
                            displayTeamInfo(selectedTeam);
                        }
                    }
                }
            });
        }

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Team findTeamByName(Season season, String teamName) {
        for (Team team : season.getTeams()) {
            if (team.getName().equals(teamName)) {
                return team;
            }
        }
        return null;
    }

    private void displayTeamInfo(Team team) {
        StringBuilder teamInfo = new StringBuilder();

        teamInfo.append("Team Name: ").append(team.getName()).append("\n");
        teamInfo.append("City: ").append(team.getCity()).append("\n");
        teamInfo.append("Arena: ").append(team.getAreana()).append("\n");
        teamInfo.append("Team ID: ").append(team.getTeam_id()).append("\n");

        teamInfo.append("Owner: ").append(team.getOwner().getName()).append("\n");

        if (!team.getGames().isEmpty()) {
            teamInfo.append("Games: \n");
            for (Game game : team.getGames()) {
                teamInfo.append(" - ").append(game.getHome_team().getName()).append(" vs ").append(game.getAway_team().getName()).append("\n");
            }
        } else {
            teamInfo.append("No games scheduled\n");
        }

        if (!team.getTeam_members().isEmpty()) {
            teamInfo.append("Team Members: \n");
            for (TeamMember member : team.getTeam_members()) {
                teamInfo.append(" - ").append(member.getName()).append("\n");
            }
        } else {
            teamInfo.append("No team members\n");
        }

        if (!team.getSponsers().isEmpty()) {
            teamInfo.append("Sponsors: \n");
            for (Sponsor sponsor : team.getSponsers()) {
                teamInfo.append(" - ").append(sponsor.getName()).append("\n");
            }
        } else {
            teamInfo.append("No sponsors\n");
        }

        JOptionPane.showMessageDialog(this, teamInfo.toString());
    }

}
