import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        StudentProfile student = new StudentProfile(
                "Ayaan",
                "23BCS1001",
                8.10,
                72,
                18,
                "NONE"
        );

        List<EligibilityRule> rules = new ArrayList<>();
        rules.add(new CGRRule(7.00));
        rules.add(new AttendanceRule(75));
        rules.add(new CreditRule(20));
        rules.add(new DisciplinaryRule());

        EligibilityEngine engine = new EligibilityEngine(rules);

        List<String> reasons = engine.evaluate(student);

        EligibilityReportFormatter formatter = new EligibilityReportFormatter();
        String report = formatter.format(student, reasons);

        System.out.println(report);

        EligibilityRepository repo = new InMemoryEligibilityRepository();
        repo.save(student);
    }
}