# es-plugin-ingest-processor

### Summary

This is a plugin that, once installed in an Elasticsearch instance, will add a field
to every document ingested via a pipeline using that plugin.

These instructions presume that Elasticsearch and Kibana are hosted in Docker containers
and started using a docker-compose.yml file (example included below).

### Build the Plugin

Run the following command from the root of the project:
```
conf
```
This will create .zip file: /build/distributions/es-plugin-ingest-processor-1.0.0-SNAPSHOT.zip

### Install the Plugin

Copy the .zip file to a volume shared with the Docker Container (e.g., /tmp).

### Run the Docker Compose File

#### docker-compose.yml

```yaml
services:
  elasticsearch:
    image: elasticsearch:8.15.1
    environment:
      - discovery.type=single-node
      - ES_JAVA_OPTS=-Xms1g -Xmx1g
      - xpack.security.enabled=false
    volumes:
      - es_data:/usr/share/elasticsearch/data
      - ./volumes/tmp:/tmp
    ports:
      - target: 9200
        published: 9200
    command: >
      sh -c "bin/elasticsearch-plugin install file:///tmp/es-plugin-ingest-processor-1.0.0-SNAPSHOT.zip --batch && exec bin/elasticsearch"

  kibana:
    image: kibana:8.15.1
    ports:
      - target: 5601
        published: 5601
    depends_on:
      - elasticsearch

volumes:
  es_data:
    driver: local
```

Check the logs after starting the Elasticsearch Docker Container to make sure no errors
were encountered.

### Test the Plugin
First, check to see that the plugin was installed properly using this command:
```
GET _cat/plugins
```

To create an ingest pipeline using the plugin:

```
PUT _ingest/pipeline/custom-field-pipeline
{
  "description": "A pipeline to add a custom field",
  "processors": [
    {
      "custom_ingest_processor": {}
    }
  ]
}
```
The "custom_ingest_processor" is the name of the plugin as set in the CustomIngestProcessor.java file.

To test the pipeline without actually indexing a document:

```
POST _ingest/pipeline/my-custom-pipeline/_simulate
{
  "docs": [
    {
      "_source": {
        "field1": "value1"
      }
    }
  ]
}
```
