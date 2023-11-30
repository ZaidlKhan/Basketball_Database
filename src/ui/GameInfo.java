package ui;

import database.DatabaseConnectionHandler;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GameInfo extends JFrame {
    private JPanel gameDataPanel;

    public GameInfo(Season season, DatabaseConnectionHandler dbHandler) {
        setSize(400, 550);
        setTitle("BASKETBALL DATABASE");
        setLocationRelativeTo(null);

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

        DefaultListModel<String> gameListModel = new DefaultListModel<>();

        for (Game game : games) {
            String str = "SSID: " + game.getSsid() + "    ||  " + teams.get(game.getHomeTeam() - 1).getName() + "  VS  " + teams.get(game.getAwayTeam() - 1).getName();
            gameListModel.addElement(str);
        }

        if (gameListModel.isEmpty()) {
            JLabel noGamesLabel = new JLabel("NO GAMES");
            noGamesLabel.setHorizontalAlignment(JLabel.CENTER);
            getContentPane().add(noGamesLabel, BorderLayout.CENTER);
        } else {
            JList<String> gameList = new JList<>(gameListModel);
            gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JScrollPane scrollPane = new JScrollPane(gameList);
            getContentPane().add(scrollPane, BorderLayout.CENTER);

            gameList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    JList<String> list = (JList<String>) evt.getSource();
                    if (evt.getClickCount() == 2) {
                        int index = list.locationToIndex(evt.getPoint());
                        Game selectedGame = games.get(index);
                        displayGameInfo(selectedGame,teams, dbHandler);
                    }
                }
            });
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setVisible(true);
    }



    private void displayGameInfo(Game game, List<Team> teams, DatabaseConnectionHandler dbHandler) {
        JDialog dialog = new JDialog(this, "Game Information", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.add(new JLabel("SSID: " + game.getSsid()));
        infoPanel.add(new JLabel("Date: " + game.getGameDate()));
        infoPanel.add(new JLabel("Home Team: " + teams.get(game.getHomeTeam() - 1).getName()));
        infoPanel.add(new JLabel("Away Team: " + teams.get(game.getAwayTeam() - 1).getName()));
        dialog.add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton refInfoButton = new JButton("Referee Info");
        refInfoButton.addActionListener(e -> displayRefereeInfo(game, dbHandler));
        buttonPanel.add(refInfoButton);

        JButton homeInfoButton = new JButton("Home Stats");
        homeInfoButton.addActionListener(e -> homeInfoButton(game, dbHandler));
        buttonPanel.add(homeInfoButton);

        JButton awayInfoButton = new JButton("Away Stats");
        awayInfoButton.addActionListener(e -> awayInfoButton(game, dbHandler));
        buttonPanel.add(awayInfoButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

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



