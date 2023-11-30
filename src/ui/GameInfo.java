package ui;

import database.DatabaseConnectionHandler;
import model.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GameInfo extends JFrame {
    private JPanel gameDataPanel;

    public GameInfo(Season season, DatabaseConnectionHandler dbHandler) {
        setSize(420, 400);
        setTitle("BASKETBALL DATABASE");
        setLocationRelativeTo(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new BorderLayout());
        JButton backButton = new JButton("Back");
        backButtonPanel.add(backButton, BorderLayout.EAST);

        backButton.addActionListener(e -> {
            this.dispose();
        });

        getContentPane().add(backButtonPanel, BorderLayout.NORTH);

        List<Game> games = dbHandler.getAllGames(season.getYear());
        List<Team> teams = dbHandler.getAllTeams();
        getContentPane().setBackground(new Color(176, 138, 78));

        DefaultListModel<String> gameListModel = new DefaultListModel<>();

        gameListModel.addElement("                                " + String.valueOf(season.getYear()));
        gameListModel.addElement("\n");
        for (Game game : games) {
            String str = "  SSID: " + game.getSsid() + "     ||   " + teams.get(game.getHomeTeam() - 1).getName().trim() + "  VS  " + teams.get(game.getAwayTeam() - 1).getName().trim();
            gameListModel.addElement(str);
        }

        if (gameListModel.isEmpty()) {
            JLabel noGamesLabel = new JLabel("NO GAMES");
            noGamesLabel.setHorizontalAlignment(JLabel.CENTER);
            getContentPane().add(noGamesLabel, BorderLayout.CENTER);
        } else {
            JList<String> gameList = new JList<>(gameListModel);
            gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            gameList.setBounds(50,30,300,300);
            gameList.setFont(new Font ("Arial", Font.BOLD,16));
            gameList.setForeground(Color.white);
            gameList.setBackground(new Color(124, 97, 55));
            getContentPane().add(gameList);
            setLayout(null);

            gameList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    JList<String> list = (JList<String>) evt.getSource();
                    if (evt.getClickCount() == 2) {
                        int index = list.locationToIndex(evt.getPoint()) - 2;
                        Game selectedGame = games.get(index);
                        displayGameInfo(selectedGame,teams, dbHandler);
                    }
                }
            });
        }


        setVisible(true);
    }



    private void displayGameInfo(Game game, List<Team> teams, DatabaseConnectionHandler dbHandler) {
        JDialog dialog = new JDialog(this, "Game Information", true);
        //dialog.setLayout(new BorderLayout());
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(null);
        dialog.setBackground(new Color(148, 119, 59));


        StringBuilder teamInfo = new StringBuilder();

        teamInfo.append("\n   SSID: ").append(game.getSsid()).append("\n\n");
        teamInfo.append("   Game Date: ").append(game.getGameDate()).append("\n\n");
        teamInfo.append("   Home Team: ").append(teams.get(game.getHomeTeam() - 1).getName()).append("\n\n");
        teamInfo.append("   Away Team: ").append(teams.get(game.getAwayTeam() - 1).getName()).append("\n\n");

        JTextArea teamInfoTextArea = new JTextArea();
        teamInfoTextArea.setFont(new Font("Arial", Font.BOLD, 16));
        //teamInfoTextArea.setOpaque(false);
        teamInfoTextArea.setEditable(false);
        teamInfoTextArea.setBounds(0, 0, 450, 400);
        teamInfoTextArea.setForeground(Color.white);
        teamInfoTextArea.setBackground(new Color(148, 119, 59));

        teamInfoTextArea.setText(teamInfo.toString());
        dialog.add(teamInfoTextArea);

        Border roundedBorder = BorderFactory.createLineBorder(Color.white, 4, true);


        JButton homeInfoButton = new JButton("Home Stats");
        homeInfoButton.setBounds(60, 180, 140, 60);
        homeInfoButton.addActionListener(e -> homeInfoButton(game, dbHandler));
        homeInfoButton.setFont(new Font("Arial",Font.BOLD,15));
        homeInfoButton.setBorder(roundedBorder);
        homeInfoButton.setBackground(Color.white);
        homeInfoButton.setFocusPainted(false);
        teamInfoTextArea.add(homeInfoButton);

        JButton awayInfoButton = new JButton("Away Stats");
        awayInfoButton.setBounds(240, 180, 140, 60);
        awayInfoButton.addActionListener(e -> awayInfoButton(game, dbHandler));
        awayInfoButton.setFont(new Font("Arial",Font.BOLD,15));
        awayInfoButton.setFocusPainted(false);
        awayInfoButton.setBackground(Color.white);
        awayInfoButton.setBorder(roundedBorder);
        teamInfoTextArea.add(awayInfoButton);

        JButton refInfoButton = new JButton("Referee Info");
        refInfoButton.setFont(new Font("Arial",Font.BOLD,15));
        refInfoButton.setBackground(Color.white);
        refInfoButton.setBounds(148, 270, 140, 60);
        refInfoButton.addActionListener(e -> displayRefereeInfo(game, dbHandler));
        refInfoButton.setFocusPainted(false);
        refInfoButton.setBorder(roundedBorder);
        teamInfoTextArea.add(refInfoButton);


        dialog.setVisible(true);
    }

    private void homeInfoButton(Game game, DatabaseConnectionHandler dbHandler) {
        StatSheet statSheet = dbHandler.getHomeStats(game.getSsid());
        JOptionPane.showMessageDialog(this,
                "Points: " + statSheet.getHome_points() +
                        "\nAssists: " + statSheet.getHome_assits() +
                        "\nRebounds: " + statSheet.getHome_rebounds() +
                        "\nSteals: " + statSheet.getHome_steals());
    }

    private void awayInfoButton(Game game, DatabaseConnectionHandler dbHandler) {
        StatSheet statSheet = dbHandler.getAwayStats(game.getSsid());
        JOptionPane.showMessageDialog(this,
                "Points: " + statSheet.getAway_points() +
                        "\nAssists: " + statSheet.getAway_assits() +
                        "\nRebounds: " + statSheet.getAway_rebounds() +
                        "\nSteals: " + statSheet.getAway_steals());
    }


    private void displayRefereeInfo(Game game, DatabaseConnectionHandler dbHandler) {
        Referee ref = dbHandler.getReferee(game.getRid());
        JOptionPane.showMessageDialog(this,
                "RefereeID: " + ref.getRef_id() +
                "\nName: " + ref.getName() +
                "\n Years Experience: " + ref.getYears_exprience() + " years");
    }

}



