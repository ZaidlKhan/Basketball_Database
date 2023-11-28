package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class TeamMemberInfo extends JFrame {
    public TeamMemberInfo(Season season) {
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

        addButton.addActionListener(e -> {
            openAddPlayerDialog(season);
        });

        getContentPane().add(backButtonPanel, BorderLayout.NORTH);

        DefaultListModel<String> memberListModel = new DefaultListModel<>();
        for (Team team : season.getTeams()) {
            for (TeamMember member : team.getTeam_members()) {
                String memberType = member instanceof Player ? "Player" : "Coach";
                memberListModel.addElement(member.getName() + " (" + memberType + ") - " + member.getTeam().getName());
            }
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
                    TeamMember selectedMember = findMemberByName(season, selectedMemberInfo.split(" \\(")[0]);
                    if (selectedMember != null) {
                        displayMemberInfo(selectedMember, season);
                    }
                }
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private TeamMember findMemberByName(Season season, String memberName) {
        for (Team team : season.getTeams()) {
            for (TeamMember member : team.getTeam_members()) {
                if (member.getName().equals(memberName)) {
                    return member;
                }
            }
        }
        return null;
    }

    private void displayMemberInfo(TeamMember member, Season season) {
        String info = "Name: " + member.getName() +
                "\nAge: " + member.getAge() +
                "\nTeam: " + member.getTeam().getName() +
                "\nContract Start: " + member.getStart_date() +
                "\nContract End: " + member.getEnd_date() +
                "\nSalary: $" + member.getSalary();

        if (member instanceof Player) {
            Player player = (Player) member;
            info += "\nPosition: " + player.getPosition();
        } else if (member instanceof Coach) {
            Coach coach = (Coach) member;
            info += "\nRole: " + coach.getRole();
        }

        JTextArea textArea = new JTextArea(info);
        textArea.setEditable(false);

        JButton deleteButton = new JButton("Delete");
        JButton modifyButton = new JButton("Modify");

        deleteButton.addActionListener(e -> deleteMember(member, season));
        modifyButton.addActionListener(e -> modifyMember(member, season));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(modifyButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JOptionPane.showConfirmDialog(this, panel, "Member Information", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
    }



    private void openAddPlayerDialog(Season season) {
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
        for (Team team : season.getTeams()) {
            teamComboBox.addItem(team.getName());
        }

        formPanel.add(new JLabel("Team:"));
        formPanel.add(teamComboBox);

        dialog.add(formPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                int salary = Integer.parseInt(salaryField.getText());
                String position = positionField.getText();
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                String team = (String) teamComboBox.getSelectedItem();

                Team teamobj = getTeamFromString(team, season);

                Random rand = new Random();
                int randomNum = rand.nextInt(10000);
                Player newPlayer = new Player(randomNum, name, teamobj, age, startDate, endDate, salary, position);
                assert teamobj != null;
                teamobj.addTeam_member(newPlayer);

                dialog.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid number format.");
            }
        });

        dialog.add(submitButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void deleteMember(TeamMember member, Season season) {
        int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this player?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            Team team = member.getTeam();
            team.removeTeam_member(member);
            this.dispose();
            new TeamMemberInfo(season);
        }
    }

    private Team getTeamFromString(String name, Season season) {
        for (Team team : season.getTeams()) {
            if (team.getName().equals(name)) {
                return team;
            }
        }
        return null;
    }

    private void modifyMember(TeamMember member, Season season) {
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
                try {
                    int age = Integer.parseInt(ageField.getText());
                    int salary = Integer.parseInt(salaryField.getText());
                    String startDate = startDateField.getText();
                    String endDate = endDateField.getText();

                    member.setAge(age);
                    member.setSalary(salary);
                    member.setStartDate(startDate);
                    member.setEndDate(endDate);

                    modifyDialog.dispose();
                    repaint();
                    revalidate();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(modifyDialog, "Please enter valid numbers for age and salary.");
                }
        });
    }



}
