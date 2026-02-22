public class InMemoryEligibilityRepository implements EligibilityRepository {

    public void save(StudentProfile profile) {
        System.out.println("Saved evaluation for roll=" + profile.roll);
    }
}