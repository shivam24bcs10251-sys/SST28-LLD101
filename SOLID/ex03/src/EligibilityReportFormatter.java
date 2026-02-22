import java.util.List;

public class EligibilityReportFormatter {

    public String format(StudentProfile profile, List<String> reasons) {

        StringBuilder sb = new StringBuilder();

        sb.append("=== Placement Eligibility ===\n");
        sb.append("Student: ")
          .append(profile.name)
          .append(" (CGR=")
          .append(String.format("%.2f", profile.cgr))
          .append(", attendance=")
          .append(profile.attendance)
          .append(", credits=")
          .append(profile.credits)
          .append(", flag=")
          .append(profile.flag)
          .append(")\n");

        if (reasons.isEmpty()) {
            sb.append("RESULT: ELIGIBLE");
        } else {
            sb.append("RESULT: NOT_ELIGIBLE\n");
            for (String reason : reasons) {
                sb.append("- ").append(reason).append("\n");
            }
        }

        return sb.toString().trim();
    }
}