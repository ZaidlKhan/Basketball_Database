package ui;

import database.DatabaseConnectionHandler;
import model.Season;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SeasonInfo extends JFrame {
    private JLabel titleLabel;
    private DefaultListModel<String> seasonListModel;
    private JList<String> seasonList;

    public SeasonInfo(DatabaseConnectionHandler dbHandler) {
        setTitle("Season Information");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        titleLabel = new JLabel("Select Season");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        List<Season> seasons = dbHandler.getAllSeasons();

        // Display season information
        seasonListModel = new DefaultListModel<>();
        for (Season season : seasons) {
            seasonListModel.addElement(season.toString());
        }

        seasonList = new JList<>(seasonListModel);
        seasonList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = seasonList.locationToIndex(e.getPoint());
                    String selectedSeason = seasonListModel.getElementAt(index);
                    // Handle the double-clicked season, you can perform actions here
                    if (selectedSeason != null) {
                        Season newSeason = seasons.get(index);
                        new GameInfo(newSeason,dbHandler);
                    }
                }
            }
        });

        add(titleLabel, BorderLayout.NORTH);
        add(new JScrollPane(seasonList), BorderLayout.CENTER);
        setVisible(true);
    }

}
