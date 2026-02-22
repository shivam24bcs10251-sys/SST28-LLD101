public class PdfExporter extends Exporter {

    protected ExportResult doExport(String content) {

        if (content.length() > 20) {
            return ExportResult.error(
                "PDF cannot handle content > 20 chars"
            );
        }

        byte[] bytes = ("PDF:" + content).getBytes();
        return ExportResult.ok(bytes);
    }
}