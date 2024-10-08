package org.example.plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.ingest.Processor;
import org.elasticsearch.plugins.IngestPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.Map;

public class CustomIngestProcessorPlugin extends Plugin implements IngestPlugin {
    private static final Logger logger = LogManager.getLogger(CustomIngestProcessorPlugin.class);

    @Override
    public Map<String, Processor.Factory> getProcessors(Processor.Parameters parameters) {
        logger.info("CustomIngestProcessorPlugin: Registering custom_ingest_processor");
        return Map.of(CustomIngestProcessor.TYPE, new CustomIngestProcessor.Factory());
    }
}
