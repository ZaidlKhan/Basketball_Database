
package ui;

import database.DatabaseConnectionHandler;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.List;

public class TeamMemberInfo extends JFrame {
    public TeamMemberInfo(DatabaseConnectionHandler dbHandler) {
        setSize(600, 550);
        setTitle("TEAM MEMBERS DATABASE");
        setLocationRelativeTo(null);

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new BorderLayout());
        JButton backButton = new JButton("Back");
        backButtonPanel.add(backButton, BorderLayout.EAST);

        backButton.addActionListener(e -> {
            this.dispose();
        });

        JButton addButton = new JButton("Add Player");
        backButtonPanel.add(addButton, BorderLayout.WEST);

        // Add Player
        addButton.addActionListener(e -> {
            openAddPlayerDialog(dbHandler);
        });

        getContentPane().add(backButtonPanel, BorderLayout.NORTH);

        DefaultListModel<String> memberListModel = new DefaultListModel<>();
        List<Team> teams = dbHandler.getAllTeams();

        for (Team team : teams) {
            memberListModel.addElement(team.getName());
            memberListModel.addElement("\n");
            for (TeamMember member : dbHandler.getAllTeamMembers(team.getTeam_id())) {
                // String memberType = member instanceof Player ? "Player" : "Coach";
                memberListModel.addElement(member.getPlayer_id() + ".  " + member.getName());
            }
            memberListModel.addElement("--------------------------------------------------------------");
            memberListModel.addElement("\n");
        }

        JList<String> memberList = new JList<>(memberListModel);
        memberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(memberList);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void openAddPlayerDialog(DatabaseConnectionHandler dbhHandler) {
        JDialog dialog = new JDialog(this, "Add Player", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField positionField = new JTextField();
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        nameField.setText("Jordan Bell");
        ageField.setText("50");
        salaryField.setText("20000");
        positionField.setText("Center second");
        startDateField.setText("2020-01-01");
        endDateField.setText("2022-01-01");

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Salary:"));
        formPanel.add(salaryField);
        formPanel.add(new JLabel("Position:"));
        formPanel.add(positionField);
        formPanel.add(new JLabel("Start Date:"));
        formPanel.add(startDateField);
        formPanel.add(new JLabel("End Date:"));
        formPanel.add(endDateField);

        JComboBox<String> teamComboBox = new JComboBox<>();
        for (Team team : dbhHandler.getAllTeams()) {
            teamComboBox.addItem(team.getName());
        }

        formPanel.add(new JLabel("Team:"));
        formPanel.add(teamComboBox);

        dialog.add(formPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
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

            JOptionPane.showMessageDialog(dialog, "Successfully added new member!");
            dialog.dispose();
            new TeamMemberInfo(dbhHandler);

        });

        dialog.add(submitButton, BorderLayout.SOUTH);
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
        TeamMember member = dbHandler.getMemberByID(tmid);

        String info = "\nName: " + member.getName() +
                "\nAge: " + member.getAge() +
                "\nTeam: " + member.getTeam().getName() +
                "\nMember ID: " + member.getPlayer_id() +
                "\n\nContract Start: " + member.getStart_date() +
                "\nContract End: " + member.getEnd_date() +
                "\n\nSalary: $" + member.getSalary();


        Player playerr = dbHandler.getPlayerByID(tmid);
        if (!playerr.getPosition().equalsIgnoreCase("coach")) {
            info += "\nPosition: " + playerr.getPosition() + "\n\n";
        } else {
            info += "\nRole: " + playerr.getPosition() + "\n\n\n";
        }

        JTextArea textArea = new JTextArea(info);
        textArea.setEditable(false);

        JButton deleteButton = new JButton("Delete");
        JButton modifyButton = new JButton("Modify");

        deleteButton.addActionListener(e -> deleteMember(member, dbHandler));
        modifyButton.addActionListener(e -> modifyMember(member, dbHandler));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(modifyButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JOptionPane.showConfirmDialog(this, panel, "Member Information", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
    }


    private void deleteMember(TeamMember member, DatabaseConnectionHandler dbHandler) {
        int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this player?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            dbHandler.deleteTeamMember(member.getPlayer_id());
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

    private void modifyMember(TeamMember member, DatabaseConnectionHandler dbHandler) {
        JDialog modifyDialog = new JDialog(this, "Modify Member", true);
        modifyDialog.setLayout(new GridLayout(0, 2));
        modifyDialog.setSize(300, 200);
        modifyDialog.setLocationRelativeTo(this);

        JTextField ageField = new JTextField(String.valueOf(member.getAge()));
        JTextField salaryField = new JTextField(String.valueOf(member.getSalary()));
        JTextField startDateField = new JTextField(member.getStart_date());
        JTextField endDateField = new JTextField(member.getEnd_date());


        modifyDialog.add(new JLabel("Age:"));
        modifyDialog.add(ageField);
        modifyDialog.add(new JLabel("Salary:"));
        modifyDialog.add(salaryField);
        modifyDialog.add(new JLabel("Contract Start:"));
        modifyDialog.add(startDateField);
        modifyDialog.add(new JLabel("Contract End:"));
        modifyDialog.add(endDateField);


        JButton saveButton = new JButton("Save");
        modifyDialog.add(saveButton);
        modifyDialog.setVisible(true);

        saveButton.addActionListener(e -> {
                int age = Integer.parseInt(ageField.getText());
                int salary = Integer.parseInt(salaryField.getText());
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();

                System.out.println(salary);
                dbHandler.updateMember(member,age,salary,startDate,endDate);
                modifyDialog.dispose();
                // repaint();
                // revalidate();
        });
    }


}
