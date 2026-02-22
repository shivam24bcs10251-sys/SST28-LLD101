public class SendResult {

    public boolean success;
    public String message;

    private SendResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static SendResult ok(String msg) {
        return new SendResult(true, msg);
    }

    public static SendResult error(String msg) {
        return new SendResult(false, msg);
    }
}