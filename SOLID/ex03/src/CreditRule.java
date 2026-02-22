public class CreditRule implements EligibilityRule {

    private int minimum;

    public CreditRule(int minimum) {
        this.minimum = minimum;
    }

    public boolean isSatisfied(StudentProfile profile) {
        return profile.credits >= minimum;
    }

    public String getFailureReason() {
        return "credits below " + minimum;
    }
}