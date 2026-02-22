import java.util.ArrayList;
import java.util.List;

public class EligibilityEngine {

    private List<EligibilityRule> rules;

    public EligibilityEngine(List<EligibilityRule> rules) {
        this.rules = rules;
    }

    public List<String> evaluate(StudentProfile profile) {

        List<String> reasons = new ArrayList<>();

        for (EligibilityRule rule : rules) {
            if (!rule.isSatisfied(profile)) {
                reasons.add(rule.getFailureReason());
            }
        }

        return reasons;
    }
}