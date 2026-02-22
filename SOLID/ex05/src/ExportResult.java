public class ExportResult {

    public boolean success;
    public String message;
    public byte[] data;

    private ExportResult(boolean success,
                         String message,
                         byte[] data) {

        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static ExportResult ok(byte[] data) {
        return new ExportResult(true,
                "OK bytes=" + data.length,
                data);
    }

    public static ExportResult error(String msg) {
        return new ExportResult(false,
                "ERROR: " + msg,
                null);
    }
}