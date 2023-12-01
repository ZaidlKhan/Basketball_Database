
package ui;

import database.DatabaseConnectionHandler;
import model.Player;
import model.Team;
import model.TeamMember;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TeamMemberInfo extends JFrame {
    public TeamMemberInfo(DatabaseConnectionHandler dbHandler) {
        setSize(615, 700);
        setTitle("TEAM MEMBERS DATABASE");
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(148, 103, 34));
        Border roundedBorder = BorderFactory.createLineBorder(Color.white, 4, true);


        JButton addButton = new JButton("Add New Player");
        addButton.setBounds(115, 530, 150, 75);
        addButton.setFont(new Font("Arial", Font.BOLD, 17));
        addButton.setForeground(Color.black);
        addButton.setBackground(Color.white);
        addButton.setBorder(roundedBorder);
        add(addButton);
        addButton.addActionListener(e -> {
            openAddPlayerDialog(dbHandler);
        });


        JButton backButton = new JButton("Back");
        backButton.setBounds(340, 530, 150, 75);
        backButton.setFont(new Font("Arial", Font.BOLD, 17));
        backButton.setBackground(Color.white);
        backButton.setForeground(Color.black);
        backButton.setBorder(roundedBorder);
        add(backButton);
        backButton.addActionListener(e -> {
            this.dispose();
        });


        DefaultListModel<String> memberListModel = new DefaultListModel<>();
        List<Team> teams = dbHandler.getAllTeams();

        memberListModel.addElement("\n");
        for (Team team : teams) {
            memberListModel.addElement("\n                                          " + team.getName());
            memberListModel.addElement("\n");
            for (TeamMember member : dbHandler.getAllTeamMembers(team.getTeam_id())) {
                memberListModel.addElement("  " + member.getPlayer_id() + ".  " + member.getName());
            }
            memberListModel.addElement("\n");
            memberListModel.addElement("   ----------------------------------------------------------------------------");
            memberListModel.addElement("\n");
        }

        JList<String> memberList = new JList<>(memberListModel);
        memberList.setFont(new Font("Arial", Font.BOLD, 19));
        memberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        memberList.setForeground(Color.white);
        memberList.setBackground(new Color(185, 130, 38));

        JScrollPane scrollPane = new JScrollPane(memberList);
        setLayout(null);
        scrollPane.setBounds(50, 40, 500, 450);

        getContentPane().add(scrollPane);

        memberList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList<String> list = (JList<String>) evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    String selectedMemberInfo = list.getModel().getElementAt(index);
                    int selectedMember = findMemberByName(selectedMemberInfo);
                    if (selectedMember != -1) {
                        displayMemberInfo(selectedMember, dbHandler);
                    }
                }
            }
        });

        setVisible(true);
    }

    private void openAddPlayerDialog(DatabaseConnectionHandler dbhHandler) {
        JDialog dialog = new JDialog(this, "Add Player", true);
        dialog.setSize(480, 600);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(null);
        JLabel nameLabel = new JLabel("Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel salaryLabel = new JLabel("Salary:");
        JLabel positionLabel = new JLabel("Position:");
        JLabel startDateLabel = new JLabel("Start Date:");
        JLabel endDateLabel = new JLabel("End Date:");
        JLabel teamLabel = new JLabel("Team:");
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField positionField = new JTextField();
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();
        JComboBox<String> teamComboBox = new JComboBox<>();
        for (Team team : dbhHandler.getAllTeams()) {
            teamComboBox.addItem(team.getName());
        }

        nameLabel.setBounds(35, 20, 150, 30);
        ageLabel.setBounds(35, 70, 150, 30);
        salaryLabel.setBounds(35, 120, 150, 30);
        positionLabel.setBounds(35, 170, 150, 30);
        startDateLabel.setBounds(35, 220, 150, 30);
        endDateLabel.setBounds(35, 270, 150, 30);
        teamLabel.setBounds(35, 320, 150, 30);

        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        ageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        salaryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        positionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        startDateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        endDateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        teamLabel.setFont(new Font("Arial", Font.BOLD, 18));

        nameField.setBounds(180, 20, 220, 35);
        ageField.setBounds(180, 70, 220, 35);
        salaryField.setBounds(180, 120, 220, 35);
        positionField.setBounds(180, 170, 220, 35);
        startDateField.setBounds(180, 220, 220, 35);
        endDateField.setBounds(180, 270, 220, 35);
        teamComboBox.setBounds(180, 320, 220, 35);

        startDateField.setText("2023-01-01");
        endDateField.setText("2023-12-31");


        dialog.add(nameLabel);
        dialog.add(ageLabel);
        dialog.add(salaryLabel);
        dialog.add(positionLabel);
        dialog.add(startDateLabel);
        dialog.add(endDateLabel);
        dialog.add(teamLabel);
        dialog.add(nameField);
        dialog.add(ageField);
        dialog.add(salaryField);
        dialog.add(positionField);
        dialog.add(startDateField);
        dialog.add(endDateField);
        dialog.add(teamComboBox);


        JButton submitButton = new JButton("ADD PLAYER");
        submitButton.setBounds(65, 420, 155, 75);
        submitButton.setBackground(Color.white);

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(260, 420, 155, 75);
        cancelButton.setBackground(Color.white);

        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                int salary = Integer.parseInt(salaryField.getText());
                String position = positionField.getText();
                Date start_date = java.sql.Date.valueOf(startDateField.getText());
                Date end_date = java.sql.Date.valueOf(endDateField.getText());

                String team = (String) teamComboBox.getSelectedItem();
                Team teamobj = getTeamFromString(team, dbhHandler);
                int tid = teamobj.getTeam_id();
                int tmid = (dbhHandler.getMaxTeamID()) + 1;

                dbhHandler.insertTeamMember(tmid, name, tid, start_date, end_date, salary, age);
                dbhHandler.insertPlayer(tmid, position);


                JOptionPane.showMessageDialog(dialog, "Successfully added new member!");
                System.out.println("Successfully added new member with tmid " + tmid);
                dialog.dispose();
                this.dispose();
                new TeamMemberInfo(dbhHandler);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid Input! Try again!");
            } catch (IllegalArgumentException ex2) {
                JOptionPane.showMessageDialog(dialog, "Invalid Date Input! Try again!");
            }


        });

        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });

        dialog.add(cancelButton);
        dialog.add(submitButton);
        dialog.setVisible(true);
    }

    private int findMemberByName(String input) {
        String[] parts = input.split("\\.");
        int num = -1;
        // Extract the number from the first part and parse it to an integer
        if (parts.length > 0) {
            try {
                num = Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format");
            }
        } else {
            System.out.println("Invalid input string");
        }
        return num;
    }


    private void displayMemberInfo(int tmid, DatabaseConnectionHandler dbHandler) {
        Border roundedBorder = BorderFactory.createLineBorder(Color.white, 4, true);
        JDialog dialog = new JDialog(this, "Player Information", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(null);
        dialog.setBackground(new Color(148, 119, 59));


        TeamMember member = dbHandler.getMemberByID(tmid);

        String info = "\n\n         Name: " + member.getName() +
                "\n         Age: " + member.getAge() +
                "\n         Team: " + member.getTeam().getName() +
                "\n         Member ID: " + member.getPlayer_id() +
                "\n\n         Contract Start: " + member.getStart_date() +
                "\n         Contract End: " + member.getEnd_date() +
                "\n\n         Salary: $" + member.getSalary();


        Player playerr = dbHandler.getPlayerByID(tmid);
        if (!playerr.getPosition().equalsIgnoreCase("coach")) {
            info += "\n         Position: " + playerr.getPosition() + "\n\n";
        } else {
            info += "\n         Role: " + playerr.getPosition() + "\n\n\n";
        }

        JTextArea textArea = new JTextArea(info);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        textArea.setBounds(0, 0, 450, 400);
        textArea.setForeground(Color.white);
        textArea.setBackground(new Color(148, 119, 59));

        JButton deleteButton = new JButton("Delete");
        JButton modifyButton = new JButton("Modify");

        deleteButton.setBounds(240, 250, 140, 60);
        deleteButton.setFont(new Font("Arial",Font.BOLD,15));
        deleteButton.setFocusPainted(false);
        deleteButton.setBackground(Color.white);
        deleteButton.setBorder(roundedBorder);

        modifyButton.setBounds(50, 250, 140, 60);
        modifyButton.setFont(new Font("Arial",Font.BOLD,15));
        modifyButton.setFocusPainted(false);
        modifyButton.setBackground(Color.white);
        modifyButton.setBorder(roundedBorder);


        deleteButton.addActionListener(e -> deleteMember(member, dbHandler));
        modifyButton.addActionListener(e -> modifyMember(member ,dialog, dbHandler, tmid, textArea));

        dialog.add(textArea);
        textArea.add(deleteButton);
        textArea.add(modifyButton);

        dialog.setVisible(true);

    }


    private void deleteMember(TeamMember member, DatabaseConnectionHandler dbHandler) {
        int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this player?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            dbHandler.deleteTeamMember(member.getPlayer_id());
            JOptionPane.showMessageDialog(this, "Successfully removed Team Member!");
            this.dispose();
            new TeamMemberInfo(dbHandler);
        }
    }

    private Team getTeamFromString(String name, DatabaseConnectionHandler dbHandler) {
        for (Team team : dbHandler.getAllTeams()) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }

    private void modifyMember(TeamMember member,JDialog dialogg, DatabaseConnectionHandler dbHandler, int tmid, JTextArea textArea) {
        JDialog modifyDialog = new JDialog(this, "Modify Member", true);
        modifyDialog.setLayout(new GridLayout(0, 2));
        modifyDialog.setSize(300, 200);
        modifyDialog.setLocationRelativeTo(this);

        JTextField ageField = new JTextField(String.valueOf(member.getAge()));
        JTextField salaryField = new JTextField(String.valueOf(member.getSalary()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startD = LocalDateTime.parse(member.getStart_date(), formatter);
        LocalDateTime endD = LocalDateTime.parse(member.getEnd_date(), formatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String start_Date = startD.format(outputFormatter);
        String end_Date = endD.format(outputFormatter);
        JTextField startDateField = new JTextField(start_Date);
        JTextField endDateField = new JTextField(end_Date);

        JComboBox<String> teamComboBox = new JComboBox<>();
        for (Team team : dbHandler.getAllTeams()) {
            teamComboBox.addItem(team.getName());
        }


        modifyDialog.add(new JLabel("Age:"));
        modifyDialog.add(ageField);
        modifyDialog.add(new JLabel("Salary:"));
        modifyDialog.add(salaryField);
        modifyDialog.add(new JLabel("Contract Start:"));
        modifyDialog.add(startDateField);
        modifyDialog.add(new JLabel("Contract End:"));
        modifyDialog.add(endDateField);
        modifyDialog.add(new JLabel("Team :"));
        modifyDialog.add(teamComboBox);


        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                int age = Integer.parseInt(ageField.getText());
                int salary = Integer.parseInt(salaryField.getText());
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();

                String team = (String) teamComboBox.getSelectedItem();
                Team teamobj = getTeamFromString(team, dbHandler);
                int teamID = teamobj.getTeam_id();

                int x = dbHandler.updateMember(member, teamID, age, salary, startDate, endDate);
                if (x == 1) {
                    JOptionPane.showMessageDialog(modifyDialog, "Successfully updated information!");
                    modifyDialog.dispose();
                    System.out.println("Successfully updated member information!");
                    modifyDialog.dispose();
                    dialogg.dispose();
                    displayMemberInfo(tmid,dbHandler);

                } else {
                    JOptionPane.showMessageDialog(modifyDialog, "Failed!, try again!");
                    System.out.println("Failed! Try Again!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input! Try again!");
            } catch (IllegalArgumentException ex2) {
                JOptionPane.showMessageDialog(this, "Invalid Date Input! Try again!");
            }

        });

        modifyDialog.add(saveButton);
        modifyDialog.setVisible(true);

    }

}
