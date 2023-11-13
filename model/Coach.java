public class Coach extends TeamMember {
    private final String role;

    Coach(int player_id, String name, Team team, int age, int start_date,
          int end_date, int salary, int role) {
        super(player_id, name, team, age, start_date, end_date, salary, role);
        this.role = role
    }

    public String getRole() {
        return role;
    }

}