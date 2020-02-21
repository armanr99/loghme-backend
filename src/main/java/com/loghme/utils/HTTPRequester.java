package com.loghme.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HTTPRequester {
    public static String get(String sourceURL) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpGet request = new HttpGet(sourceURL);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    System.out.println(String.format("Fetching data failed with status code %d", response.getStatusLine().getStatusCode()));
                } else {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        return EntityUtils.toString(entity, UTF_8);
                    }
                }
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}
