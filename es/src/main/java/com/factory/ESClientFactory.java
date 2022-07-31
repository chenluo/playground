package com.factory;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ESClientFactory {
    private static volatile RestHighLevelClient client;

    public static RestHighLevelClient getClient() {
        if (client == null) {
            synchronized (ESClientFactory.class) {
                if (client == null) {
//                    final CredentialsProvider credentialsProvider =
//                            new BasicCredentialsProvider();
//                    credentialsProvider.setCredentials(AuthScope.ANY,
//                            new UsernamePasswordCredentials("elastic", "81Q=+aj*i3N36UqVMgq0"));

                    RestClientBuilder builder = RestClient.builder(
                            new HttpHost("localhost", 9200, "http"));
//                            .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                                @Override
//                                public HttpAsyncClientBuilder customizeHttpClient(
//                                        HttpAsyncClientBuilder httpClientBuilder) {
//                                    return httpClientBuilder
//                                            .setDefaultCredentialsProvider(credentialsProvider);
//                                }
//                            });
                    client = new RestHighLevelClient(builder);
                }
            }
        }
        return client;
    }
}
