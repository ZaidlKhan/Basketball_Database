package ui;

import database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    private JLabel titleLabel;

    public MainMenu(DatabaseConnectionHandler dbHandler) {
        setTitle("BASKETBALL ORACLE DATABASE APPLICATION ");
        setSize(1000, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        titleLabel = new JLabel("BASKETBALL DATABASE");
        titleLabel.setForeground(Color.white);
        add(titleLabel);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        updateTitleSize(30);
        getContentPane().setBackground(new Color(140, 108, 41));

        int buttonWidth = 220;
        int buttonHeight = 100;

        ImageIcon imageIcon = new ImageIcon("src/giphy.gif");
        JLabel imageLabel = new JLabel();
        JLabel imageLabel2 = new JLabel();
        JLabel imageLabel3 = new JLabel();
        imageLabel.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(150, -1, (int) (Image.SCALE_DEFAULT + 0.5))));
        imageLabel.setBounds(170,160,150,100);
        imageLabel2.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(150, -1, (int) (Image.SCALE_DEFAULT + 0.5))));
        imageLabel2.setBounds(430,160,150,100);
        imageLabel3.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(150, -1, (int) (Image.SCALE_DEFAULT + 0.5))));
        imageLabel3.setBounds(685,160,150,100);
        add(imageLabel);
        add(imageLabel2);
        add(imageLabel3);

        JButton teamInfobutton = new JButton("TEAM INFORMATION");
        JButton gameInfobutton = new JButton("GAME INFORMATION");
        JButton teamMemberInfobutton = new JButton("PLAYER INFORMATION");
        JButton exitButton = new JButton("Exit");

        teamInfobutton.setBounds(130, 310, buttonWidth, buttonHeight);
        gameInfobutton.setBounds(390, 310, buttonWidth, buttonHeight);
        teamMemberInfobutton.setBounds(650, 310, buttonWidth, buttonHeight);
        exitButton.setBounds(820, 460, buttonWidth - 100, buttonHeight - 40);

        Font buttonFont = teamInfobutton.getFont();
        teamInfobutton.setFont(new Font(buttonFont.getName(), Font.BOLD, (int)16.5));
        gameInfobutton.setFont(new Font(buttonFont.getName(), Font.BOLD, (int)16.5));
        teamMemberInfobutton.setFont(new Font(buttonFont.getName(), Font.BOLD, (int)17.5));
        exitButton.setFont(new Font(buttonFont.getName(), Font.BOLD, (int)13));

        teamInfobutton.setBackground(new Color(183, 121, 28));
        teamInfobutton.setForeground(Color.white);
        gameInfobutton.setBackground(new Color(183, 121, 28));
        gameInfobutton.setForeground(Color.white);
        teamMemberInfobutton.setBackground(new Color(183, 121, 28));
        teamMemberInfobutton.setForeground(Color.white);
        exitButton.setBackground(Color.white);
        exitButton.setForeground(Color.black);

        Border roundedBorder = BorderFactory.createLineBorder(Color.white, 4, true);
        teamInfobutton.setBorder(roundedBorder);
        gameInfobutton.setBorder(roundedBorder);
        teamMemberInfobutton.setBorder(roundedBorder);

        teamMemberInfobutton.setFocusPainted(false);
        teamInfobutton.setFocusPainted(false);
        gameInfobutton.setFocusPainted(false);

        add(teamInfobutton);
        add(gameInfobutton);
        add(teamMemberInfobutton);
        add(exitButton);

        teamInfobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TeamInfo(dbHandler);
            }
        });

        gameInfobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SeasonInfo(dbHandler);
            }
        });

        teamMemberInfobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TeamMemberInfo(dbHandler);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        setVisible(true);
    }

    private void updateTitleSize(int newSize) {
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, newSize));
        titleLabel.setBounds(330, 80, titleLabel.getPreferredSize().width, titleLabel.getPreferredSize().height);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
    }


}
