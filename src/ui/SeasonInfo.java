package ui;

import database.DatabaseConnectionHandler;
import model.Season;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SeasonInfo extends JFrame {

    private JLabel titleLabel;

    public SeasonInfo(DatabaseConnectionHandler dbHandler) {
        setTitle("Season Information");
        setSize(600, 400);
        setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Border roundedBorder = BorderFactory.createLineBorder(Color.white, 4, true);
        titleLabel = new JLabel("Select Season Year");
        titleLabel.setForeground(Color.white);
        add(titleLabel);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        updateTitleSize(30);
        getContentPane().setBackground(new Color(169, 122, 26));

        int buttonWidth = 140;
        int buttonHeight = 80;

        List<Season> seasons = dbHandler.getAllSeasons();
        List<String> years = new ArrayList<>();

        for (Season season : seasons) {
            years.add(String.valueOf(season.getYear()));
        }
        JButton button1, button2, button3, button4, button5;
        if (years.isEmpty()) {
            button1 = new JButton("Null");
            button2 = new JButton("Null");
            button3 = new JButton("Null");
            button4 = new JButton("Null");
            button5 = new JButton("Null");
        } else {
            button1 = new JButton(years.get(0).trim());
            button2 = new JButton(years.get(1).trim());
            button3 = new JButton(years.get(2).trim());
            button4 = new JButton(years.get(3).trim());
            button5 = new JButton(years.get(4).trim());
        }

        button1.setBounds(70, 130, buttonWidth, buttonHeight);
        button2.setBounds(230, 130, buttonWidth, buttonHeight);
        button3.setBounds(390, 130, buttonWidth, buttonHeight);
        button4.setBounds(70, 230, buttonWidth, buttonHeight);
        button5.setBounds(230, 230, buttonWidth, buttonHeight);

        Font buttonFont = button1.getFont();
        button1.setFont(new Font(buttonFont.getName(), Font.BOLD, 17));
        button2.setFont(new Font(buttonFont.getName(), Font.BOLD, 17));
        button3.setFont(new Font(buttonFont.getName(), Font.BOLD, 17));
        button4.setFont(new Font(buttonFont.getName(), Font.BOLD, 17));
        button5.setFont(new Font(buttonFont.getName(), Font.BOLD, 17));

        button1.setBackground(new Color(155, 103, 24));
        button1.setForeground(Color.white);
        button2.setBackground(new Color(155, 103, 24));
        button2.setForeground(Color.white);
        button3.setBackground(new Color(155, 103, 24));
        button3.setForeground(Color.white);
        button4.setBackground(new Color(155, 103, 24));
        button4.setForeground(Color.white);
        button5.setBackground(new Color(155, 103, 24));
        button5.setForeground(Color.white);

        button1.setBorder(roundedBorder);
        button1.setFocusPainted(false);
        button2.setBorder(roundedBorder);
        button2.setFocusPainted(false);
        button3.setBorder(roundedBorder);
        button3.setFocusPainted(false);
        button4.setBorder(roundedBorder);
        button4.setFocusPainted(false);
        button5.setBorder(roundedBorder);
        button5.setFocusPainted(false);

        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!seasons.isEmpty()) {
                    Season newSeason = seasons.get(0);
                    new GameInfo(newSeason,dbHandler);
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!seasons.isEmpty()) {
                    Season newSeason = seasons.get(1);
                    new GameInfo(newSeason,dbHandler);
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!seasons.isEmpty()) {
                    Season newSeason = seasons.get(2);
                    new GameInfo(newSeason,dbHandler);
                }
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!seasons.isEmpty()) {
                    Season newSeason = seasons.get(3);
                    new GameInfo(newSeason,dbHandler);
                }
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!seasons.isEmpty()) {
                    Season newSeason = seasons.get(4);
                    new GameInfo(newSeason,dbHandler);
                }
            }
        });
        setVisible(true);

        if (seasons.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Failed to load seasons!");
            this.dispose();
        }



    }

    private void updateTitleSize(int newSize) {
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, newSize));
        titleLabel.setBounds(160, 40, titleLabel.getPreferredSize().width, titleLabel.getPreferredSize().height);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
    }
}
