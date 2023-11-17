package ui;

import model.Season;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame{

    private Season season;

    public MainMenu(Season season) {
        setSize(700, 550);
        setTitle("BASKETBALL DATABASE");
        setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon("src/giphy.gif");
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(150, -1, Image.SCALE_DEFAULT)));

        JLabel label = new JLabel("BASKETBALL DATABASE");
        label.setForeground(Color.white);

        JPanel buttonPanel = getButtonPanel(label, season, imageLabel);
        buttonPanel.setBackground(new Color(186, 130, 181));
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JPanel getButtonPanel(JLabel imageLabel, Season season, JLabel imageLabel2) {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        buttonPanel.add(imageLabel2, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        buttonPanel.add(gameInfoButton(season), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        buttonPanel.add(teamInfoButton(season), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        buttonPanel.add(playerInfoButton(season), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        buttonPanel.add(imageLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        String start = String.format(season.getStart_date());
        buttonPanel.add(new JLabel("Start Date: " + start), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        String end = String.valueOf(season.getStart_date());
        buttonPanel.add(new JLabel("End Date: " + end), gridBagConstraints);

        return buttonPanel;
    }

    public JButton gameInfoButton(Season season) {
        JButton gameButton = new JButton("View Game Info");
        gameButton.setPreferredSize(new Dimension(150, 50));
        gameButton.addActionListener(e -> {
            this.dispose();
            new GameInfo(season);
        });
        return gameButton;
    }

    public JButton teamInfoButton(Season season) {
        JButton teamButton = new JButton("View Team Info");
        teamButton.setPreferredSize(new Dimension(150, 50));
        teamButton.addActionListener(e -> {
            this.dispose();
            new TeamInfo(season);
        });
        return teamButton;
    }

    public JButton playerInfoButton(Season season) {
        JButton playerButton = new JButton("View Player Info");
        playerButton.setPreferredSize(new Dimension(150, 50));
        playerButton.addActionListener(e -> {
            this.dispose();
            new TeamMemberInfo(season);

        });
        return playerButton;
    }
}