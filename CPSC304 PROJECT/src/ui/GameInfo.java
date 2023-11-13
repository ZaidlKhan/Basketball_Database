package ui;

import model.Game;
import model.Season;

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

        DefaultListModel<Game> gameListModel = new DefaultListModel<>();
        for (Game game : season.getGames()) {
            gameListModel.addElement(game);
        }

        if (gameListModel.isEmpty()) {
            JLabel noGamesLabel = new JLabel("NO GAMES");
            noGamesLabel.setHorizontalAlignment(JLabel.CENTER);
            getContentPane().add(noGamesLabel, BorderLayout.CENTER);
        } else {
            JList<Game> gameList = new JList<>(gameListModel);
            gameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JScrollPane scrollPane = new JScrollPane(gameList);
            getContentPane().add(scrollPane, BorderLayout.CENTER);

            gameList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    @SuppressWarnings("unchecked")
                    JList<Game> list = (JList<Game>) evt.getSource();
                    if (evt.getClickCount() == 2) {
                        int index = list.locationToIndex(evt.getPoint());
                        Game selectedGame = list.getModel().getElementAt(index);
                        displayGameInfo(selectedGame);
                    }
                }
            });
        }

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void displayGameInfo(Game game) {
        // Implement this method to display information about the selected game
        JOptionPane.showMessageDialog(this, "Home Team: " + game.getHome_team().getName() +
                "\nAway Team: " + game.getAway_team().getName());
    }
}
