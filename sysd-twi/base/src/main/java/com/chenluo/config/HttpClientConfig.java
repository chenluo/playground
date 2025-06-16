package com.chenluo.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfig {
    @Bean
    public RestTemplate restTemplate() {
        // Use the custom request factory that is configured with a connection pool
        return new RestTemplate(clientHttpRequestFactory());
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient());
        return clientHttpRequestFactory;
    }

    @Bean
    public CloseableHttpClient httpClient() {
        // Configure the request timeouts
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(3000)) // Timeout for establishing a connection
                .setResponseTimeout(Timeout.ofMilliseconds(3000)) // Timeout for getting a connection from the pool
                .build();

        return HttpClientBuilder.create()
                .setConnectionManager(poolingConnectionManager()) // Set the connection manager
                .setDefaultRequestConfig(requestConfig) // Set the default request configuration
                .build();
    }

    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();

        // Set the maximum total connections
        poolingConnectionManager.setMaxTotal(200);

        // Set the maximum connections per route (per host)
        poolingConnectionManager.setDefaultMaxPerRoute(120);

        return poolingConnectionManager;
    }
}
