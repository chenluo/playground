package com.data;

import com.factory.ESClientFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EsIndexGenerator {
    private static final String INDEX_SETTINGS = """
            {
                "index": {
                    "number_of_shards": 3,
                    "number_of_replicas": 1
                }
            }
            """;
    private static final String MAPPING_SETTINGS = """
            {
                "properties": {
                    "key": {"type":"keyword"},
                    "value": {"type":"text"},
                    "timestamp": {"type":"date"}
                }
            }
            """;
    private static final String INDEX_NAME = "my-index";
    private final RestHighLevelClient client = ESClientFactory.getClient();
    private final Logger logger = LoggerFactory.getLogger(EsIndexGenerator.class);

    public static void main(String[] args) {
        new EsIndexGenerator().generateEsIndex();
    }

    public void generateEsIndex() {
        try {
            boolean pong = client.ping(RequestOptions.DEFAULT);
            logger.info("index info: {}", pong);
            CreateIndexRequest createIndexRequest =
                    new CreateIndexRequest(INDEX_NAME).settings(INDEX_SETTINGS, XContentType.JSON)
                            .mapping(MAPPING_SETTINGS, XContentType.JSON);
            boolean exists = client.indices()
                    .exists(new GetIndexRequest(INDEX_NAME), RequestOptions.DEFAULT);
            if (exists) {
                logger.warn("{} already exists", INDEX_NAME);
                return;
            }
            client.indices().create(createIndexRequest, RequestOptions.DEFAULT);

            GetMappingsResponse mapping = client.indices()
                    .getMapping(new GetMappingsRequest().indices(INDEX_NAME),
                            RequestOptions.DEFAULT);
            logger.info("created mapping: {}", mapping);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
