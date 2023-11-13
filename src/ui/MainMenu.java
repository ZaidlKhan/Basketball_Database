package ui;

import model.Season;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame{

    private Season season;

    public MainMenu(Season season) {
        setSize(400, 550);
        setTitle("BASKETBALL DATABASE");
        setLocationRelativeTo(null);

        JLabel label = new JLabel("BASKETBALL DATABASE");
        label.setForeground(Color.white);

        JPanel buttonPanel = getButtonPanel(label, season);
        buttonPanel.setBackground(new Color(186, 130, 181));
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JPanel getButtonPanel(JLabel imageLabel, Season season) {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        buttonPanel.add(gameInfoButton(season), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        buttonPanel.add(teamInfoButton(season), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        buttonPanel.add(playerInfoButton(season), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        buttonPanel.add(imageLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        String start = String.valueOf(season.getStart_date());
        buttonPanel.add(new JLabel("Start Date" + start), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        String end = String.valueOf(season.getStart_date().toString());
        buttonPanel.add(new JLabel("Start Date" + end), gridBagConstraints);

        return buttonPanel;
    }

    public JButton gameInfoButton(Season season) {
        JButton gameButton = new JButton("View Game Info");
        gameButton.setPreferredSize(new Dimension(150, 50));
        gameButton.addActionListener(e -> {
            new GameInfo(season);
        });
        return gameButton;
    }

    public JButton teamInfoButton(Season season) {
        JButton teamButton = new JButton("View Team Info");
        teamButton.setPreferredSize(new Dimension(150, 50));
        teamButton.addActionListener(e -> {
            System.out.println("open team tab");
        });
        return teamButton;
    }

    public JButton playerInfoButton(Season season) {
        JButton playerButton = new JButton("View Player Info");
        playerButton.setPreferredSize(new Dimension(150, 50));
        playerButton.addActionListener(e -> {
            System.out.println("open player info");

        });
        return playerButton;
    }
}