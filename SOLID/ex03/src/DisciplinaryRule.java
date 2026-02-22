public class DisciplinaryRule implements EligibilityRule {

    public boolean isSatisfied(StudentProfile profile) {
        return profile.flag.equals("NONE");
    }

    public String getFailureReason() {
        return "disciplinary flag present";
    }
}