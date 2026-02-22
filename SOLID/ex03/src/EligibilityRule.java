public interface EligibilityRule {

    boolean isSatisfied(StudentProfile profile);

    String getFailureReason();
}