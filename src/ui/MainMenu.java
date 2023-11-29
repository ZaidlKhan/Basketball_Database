package ui;

import database.DatabaseConnectionHandler;
import model.Season;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    private Season season;

    public MainMenu(DatabaseConnectionHandler dbHandler) {
        setSize(800, 600);
        setTitle("BASKETBALL DATABASE");
        setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon("src/giphy.gif");
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(150, -1, Image.SCALE_DEFAULT)));

        JLabel label = new JLabel("BASKETBALL DATABASE");
        label.setForeground(Color.white);

        JPanel buttonPanel = getButtonPanel(label, imageLabel, dbHandler);
        buttonPanel.setBackground(new Color(186, 130, 181));
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JPanel getButtonPanel(JLabel imageLabel, JLabel imageLabel2, DatabaseConnectionHandler dbHandler) {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        buttonPanel.add(imageLabel2, gridBagConstraints);

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        buttonPanel.add(gameInfoButton(dbHandler), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        buttonPanel.add(teamInfoButton(dbHandler), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        buttonPanel.add(playerInfoButton(dbHandler), gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        buttonPanel.add(imageLabel, gridBagConstraints);


        return buttonPanel;
    }

    public JButton gameInfoButton(DatabaseConnectionHandler dbHandler) {
        JButton gameButton = new JButton("View Game Info");
        gameButton.setPreferredSize(new Dimension(150, 50));
        gameButton.addActionListener(e -> {
            new SeasonInfo(dbHandler);
        });
        return gameButton;
    }

    public JButton teamInfoButton(DatabaseConnectionHandler dbHandler) {
        JButton teamButton = new JButton("View Team Info");
        teamButton.setPreferredSize(new Dimension(150, 50));
        teamButton.addActionListener(e -> {
             new TeamInfo(dbHandler);
        });
        return teamButton;
    }

    public JButton playerInfoButton(DatabaseConnectionHandler dbHandler) {
        JButton playerButton = new JButton("View Player Info");
        playerButton.setPreferredSize(new Dimension(150, 50));
        playerButton.addActionListener(e -> {
            new TeamMemberInfo(dbHandler);

        });
        return playerButton;
    }
}
