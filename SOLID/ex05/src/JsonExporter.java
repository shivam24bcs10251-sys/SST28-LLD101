public class JsonExporter extends Exporter {

    protected ExportResult doExport(String content) {

        String json = "{ \"data\": \"" +
                content.replace("\"", "\\\"") +
                "\" }";

        byte[] bytes = json.getBytes();
        return ExportResult.ok(bytes);
    }
}