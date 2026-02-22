public abstract class Exporter {

    public ExportResult export(ExportRequest request) {

        // Base precondition handling (uniform)
        if (request == null || request.content == null) {
            return ExportResult.error("Content is null");
        }

        return doExport(request.content);
    }

    protected abstract ExportResult doExport(String content);
}