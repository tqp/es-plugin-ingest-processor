package org.example.plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.ingest.AbstractProcessor;
import org.elasticsearch.ingest.IngestDocument;
import org.elasticsearch.ingest.Processor;

import java.util.Map;

public class CustomIngestProcessor extends AbstractProcessor {
    private static final Logger logger = LogManager.getLogger(CustomIngestProcessor.class);

    public static final String TYPE = "custom_ingest_processor"; // This will be the name of the processor.

    CustomIngestProcessor(String tag, String description) {
        super(tag, description);
    }

    @Override
    public IngestDocument execute(IngestDocument ingestDocument) throws Exception {
        logger.info("CustomIngestProcessor: Executing custom_ingest_processor");
        ingestDocument.setFieldValue("custom_field", "custom_value");
        return ingestDocument;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public static final class Factory implements Processor.Factory {
        @Override
        public Processor create(Map<String, Processor.Factory> registry, String processorTag,
                                String description, Map<String, Object> config) {
            logger.info("CustomIngestProcessor: Creating custom_ingest_processor");
            return new CustomIngestProcessor(processorTag, description);
        }
    }
}
