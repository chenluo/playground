package com.data;

import com.factory.ESClientFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EsIndexGenerator {
    private final RestHighLevelClient client = ESClientFactory.getClient();
    private final Logger logger = LoggerFactory.getLogger(EsIndexGenerator.class);

    public static void main(String[] args) {
        new EsIndexGenerator().generateEsIndex();
    }

    public void generateEsIndex() {
        try {
            boolean pong = client.ping(RequestOptions.DEFAULT);
            logger.info("index info: {}", pong);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
