package ui;

import model.Coach;
import model.Player;
import model.TeamMember;
import model.Team;
import model.Season;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TeamMemberInfo extends JFrame {
    public TeamMemberInfo(Season season) {
        setSize(600, 550);
        setTitle("TEAM MEMBERS DATABASE");
        setLocationRelativeTo(null);

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
                        displayMemberInfo(selectedMember);
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

    private void displayMemberInfo(TeamMember member) {
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

        JOptionPane.showMessageDialog(this, info);
    }
}
