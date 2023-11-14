package ui;

import model.Game;
import model.Season;
import model.StatSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameInfo extends JFrame {
    private JPanel gameDataPanel;

    public GameInfo(Season season) {
        setSize(400, 550);
        setTitle("BASKETBALL DATABASE");
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

        DefaultListModel<String> gameListModel = new DefaultListModel<>();
        for (Game game : season.getGames()) {
            gameListModel.addElement(game.getHome_team().getName() + " vs " + game.getAway_team().getName());
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
                        String selectedGameString = list.getModel().getElementAt(index);
                        Game selectedGame = findGameByString(season, selectedGameString);
                        if (selectedGame != null) {
                            displayGameInfo(selectedGame);
                        }
                    }
                }
            });
        }

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Game findGameByString(Season season, String gameString) {
        for (Game game : season.getGames()) {
            String gameDesc = game.getHome_team().getName() + " vs " + game.getAway_team().getName();
            if (gameDesc.equals(gameString)) {
                return game;
            }
        }
        return null;
    }

    private void displayGameInfo(Game game) {
        JDialog dialog = new JDialog(this, "Game Information", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.add(new JLabel("GameID: " + game.getGame_id()));
        infoPanel.add(new JLabel("Date: " + game.getDate()));
        infoPanel.add(new JLabel("Home Team: " + game.getHome_team().getName()));
        infoPanel.add(new JLabel("Away Team: " + game.getAway_team().getName()));
        dialog.add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton refInfoButton = new JButton("Referee Info");
        refInfoButton.addActionListener(e -> displayRefereeInfo(game));
        buttonPanel.add(refInfoButton);

        JButton statsSheetButton = new JButton("Statsheet Info");
        statsSheetButton.addActionListener(e -> displayStatsheetInfo(game));
        buttonPanel.add(statsSheetButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void displayRefereeInfo(Game game) {
        JOptionPane.showMessageDialog(this,
                "RefereeID: " + game.getReferee().getRef_id() +
                "\nName: " + game.getReferee().getName() +
                "\n Years Experience: " + game.getReferee().getYears_exprience() + " years");
    }

    private void displayStatsheetInfo(Game game) {
        StatSheet stats = game.getStatSheet();

        String statsMessage = "Statsheet ID: " + stats.getStatsheet_id() + "\n" +
                "Home Points: " + stats.getHome_points() + "\n" +
                "Away Points: " + stats.getAway_points() + "\n" +
                "Home Rebounds: " + stats.getHome_rebounds() + "\n" +
                "Away Rebounds: " + stats.getAway_rebounds() + "\n" +
                "Home Assists: " + stats.getHome_assits() + "\n" +
                "Away Assists: " + stats.getAway_assits() + "\n" +
                "Home Steals: " + stats.getHome_steals() + "\n" +
                "Away Steals: " + stats.getAway_steals();

        JOptionPane.showMessageDialog(this, statsMessage);
    }
}
