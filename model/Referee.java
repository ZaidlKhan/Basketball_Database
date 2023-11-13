public class Referee {
    private final int ref_id;
    private final String name;
    private final int years_exprience;

    Referee(int ref_id, String name, int years_exprience) {
        this.ref_id = ref_id;
        this.name = name;
        this.years_exprience = years_exprience;
    }

    public String getName() {
        return name;
    }

    public int getRef_id() {
        return ref_id;
    }

    public int getYears_exprience() {
        return years_exprience;
    }
}