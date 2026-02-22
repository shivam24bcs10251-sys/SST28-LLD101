import java.util.ArrayList;
import java.util.List;

public class AuditService {

    private List<String> entries = new ArrayList<>();

    public void record(SendResult result) {
        entries.add(result.message);
    }

    public void printSummary() {
        System.out.println("AUDIT entries=" + entries.size());
    }
}