package org.example.plugin;

import org.elasticsearch.ingest.Processor;
import org.elasticsearch.plugins.IngestPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.Map;

public class CustomIngestProcessorPlugin extends Plugin implements IngestPlugin {

    @Override
    public Map<String, Processor.Factory> getProcessors(Processor.Parameters parameters) {
        return Map.of(CustomIngestProcessor.TYPE, new CustomIngestProcessor.Factory());
    }
}
