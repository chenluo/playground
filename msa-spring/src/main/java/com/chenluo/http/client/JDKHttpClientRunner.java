package com.chenluo.http.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JDKHttpClientRunner implements HttpRunner {
    static HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    @Override
    public void run(int i, String url) {
        for (int j = 0; j < i; j++) {
            URI uri = null;
            try {
                uri = new URI(url);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            HttpResponse<String> res = null;
            try {
                res = client.send(
                        HttpRequest.newBuilder().GET().uri(uri).build(),
                        HttpResponse.BodyHandlers.ofString()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(res.body());
        }
    }
}
