public class AttendanceRule implements EligibilityRule {

    private int minimum;

    public AttendanceRule(int minimum) {
        this.minimum = minimum;
    }

    public boolean isSatisfied(StudentProfile profile) {
        return profile.attendance >= minimum;
    }

    public String getFailureReason() {
        return "attendance below " + minimum;
    }
}