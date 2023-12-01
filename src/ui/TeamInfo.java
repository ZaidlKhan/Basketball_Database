package ui;

import database.DatabaseConnectionHandler;
import model.Team;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TeamInfo extends JFrame {

    private JLabel titleLabel;

    public TeamInfo(DatabaseConnectionHandler dbHandler) {


        setTitle("Team Information");
        setSize(600, 400);
        setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Border roundedBorder = BorderFactory.createLineBorder(Color.white, 4, true);

        titleLabel = new JLabel("Select Team");
        titleLabel.setForeground(Color.white);

        add(titleLabel);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        updateTitleSize(30);
        getContentPane().setBackground(new Color(173, 133, 50));

        int buttonWidth = 140;
        int buttonHeight = 80;

        JButton button1, button2, button3, button4, button5;
        List<Team> teams = dbHandler.getAllTeams();

        if (teams.isEmpty()) {
            button1 = new JButton("Null");
            button2 = new JButton("Null");
            button3 = new JButton("Null");
            button4 = new JButton("Null");
            button5 = new JButton("Null");
        } else {
            button1 = new JButton(teams.get(0).getName().trim());
            button2 = new JButton(teams.get(1).getName().trim());
            button3 = new JButton(teams.get(2).getName().trim());
            button4 = new JButton(teams.get(3).getName().trim());
            button5 = new JButton(teams.get(4).getName().trim());
        }



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
                if (!teams.isEmpty()) {
                    new TeamInfoWindow(teams.get(0),dbHandler);
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!teams.isEmpty()) {
                    new TeamInfoWindow(teams.get(1),dbHandler);
                }
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!teams.isEmpty()) {
                    new TeamInfoWindow(teams.get(2),dbHandler);
                }
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!teams.isEmpty()) {
                    new TeamInfoWindow(teams.get(3),dbHandler);
                }
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!teams.isEmpty()) {
                    new TeamInfoWindow(teams.get(4),dbHandler);
                }
            }
        });


        setVisible(true);

        if (teams.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Failed to load teams!");
            this.dispose();
        }


    }

    private void updateTitleSize(int newSize) {
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, newSize));
        titleLabel.setBounds(215, 45, titleLabel.getPreferredSize().width, titleLabel.getPreferredSize().height);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
    }

}
