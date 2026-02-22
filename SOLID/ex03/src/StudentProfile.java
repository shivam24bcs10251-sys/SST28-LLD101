public class StudentProfile {

    public String name;
    public String roll;
    public double cgr;
    public int attendance;
    public int credits;
    public String flag;

    public StudentProfile(String name,
                          String roll,
                          double cgr,
                          int attendance,
                          int credits,
                          String flag) {

        this.name = name;
        this.roll = roll;
        this.cgr = cgr;
        this.attendance = attendance;
        this.credits = credits;
        this.flag = flag;
    }
}