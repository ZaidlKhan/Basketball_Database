package ui;

import database.DatabaseConnectionHandler;
import model.Season;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SeasonInfo extends JFrame {

    private JLabel titleLabel;

    public SeasonInfo(DatabaseConnectionHandler dbHandler) {
        setTitle("Season Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        titleLabel = new JLabel("Select Season Year");
        add(titleLabel);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        updateTitleSize(30);

        int buttonWidth = 140;
        int buttonHeight = 80;

        List<Season> seasons = dbHandler.getAllSeasons();
        DefaultListModel<String> seasonListModel = new DefaultListModel<>();
        List<String> years = new ArrayList<>();

        for (Season season : seasons) {
            seasonListModel.addElement(season.toString());
            years.add(String.valueOf(season.getYear()));
        }

        JButton button1 = new JButton(years.get(0));
        JButton button2 = new JButton(years.get(1));
        JButton button3 = new JButton(years.get(2));
        JButton button4 = new JButton(years.get(3));
        JButton button5 = new JButton(years.get(4));

        button1.setBounds(70, 130, buttonWidth, buttonHeight);
        button2.setBounds(230, 130, buttonWidth, buttonHeight);
        button3.setBounds(390, 130, buttonWidth, buttonHeight);
        button4.setBounds(70, 230, buttonWidth, buttonHeight);
        button5.setBounds(230, 230, buttonWidth, buttonHeight);

        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);



        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSeason = seasonListModel.getElementAt(0);
                // Handle the double-clicked season, you can perform actions here
                if (selectedSeason != null) {
                    Season newSeason = seasons.get(0);
                    new GameInfo(newSeason,dbHandler);
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSeason = seasonListModel.getElementAt(1);
                // Handle the double-clicked season, you can perform actions here
                if (selectedSeason != null) {
                    Season newSeason = seasons.get(1);
                    new GameInfo(newSeason,dbHandler);
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSeason = seasonListModel.getElementAt(2);
                // Handle the double-clicked season, you can perform actions here
                if (selectedSeason != null) {
                    Season newSeason = seasons.get(2);
                    new GameInfo(newSeason,dbHandler);
                }
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSeason = seasonListModel.getElementAt(3);
                // Handle the double-clicked season, you can perform actions here
                if (selectedSeason != null) {
                    Season newSeason = seasons.get(3);
                    new GameInfo(newSeason,dbHandler);
                }
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSeason = seasonListModel.getElementAt(4);
                // Handle the double-clicked season, you can perform actions here
                if (selectedSeason != null) {
                    Season newSeason = seasons.get(4);
                    new GameInfo(newSeason,dbHandler);
                }
            }
        });
        setVisible(true);



    }


    private void updateTitleSize(int newSize) {
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.PLAIN, newSize));
        titleLabel.setBounds(160, 40, titleLabel.getPreferredSize().width, titleLabel.getPreferredSize().height);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
    }

}
