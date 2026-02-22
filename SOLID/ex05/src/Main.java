public class Main {

    public static void main(String[] args) {

        System.out.println("=== Export Demo ===");

        ExportRequest request =
                new ExportRequest("This content is definitely long.");

        Exporter pdf = new PdfExporter();
        Exporter csv = new CsvExporter();
        Exporter json = new JsonExporter();

        ExportResult pdfResult = pdf.export(request);
        ExportResult csvResult = csv.export(request);
        ExportResult jsonResult = json.export(request);

        System.out.println("PDF: " + pdfResult.message);
        System.out.println("CSV: " + csvResult.message);
        System.out.println("JSON: " + jsonResult.message);
    }
}