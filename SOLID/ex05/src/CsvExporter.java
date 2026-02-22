public class CsvExporter extends Exporter {

    protected ExportResult doExport(String content) {

        // Proper CSV escaping
        String escaped = content
                .replace("\"", "\"\"");

        if (escaped.contains(",") || escaped.contains("\n")) {
            escaped = "\"" + escaped + "\"";
        }

        byte[] bytes = escaped.getBytes();
        return ExportResult.ok(bytes);
    }
}