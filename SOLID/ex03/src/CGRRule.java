public class CGRRule implements EligibilityRule {

    private double minimum;

    public CGRRule(double minimum) {
        this.minimum = minimum;
    }

    public boolean isSatisfied(StudentProfile profile) {
        return profile.cgr >= minimum;
    }

    public String getFailureReason() {
        return "CGR below " + minimum;
    }
}