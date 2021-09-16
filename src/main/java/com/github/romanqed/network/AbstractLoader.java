package com.github.romanqed.network;


import com.github.romanqed.util.Checks;
import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

import java.io.IOException;
import java.net.URL;

public abstract class AbstractLoader implements Loader {
    protected UnirestInstance client;

    public AbstractLoader(UnirestInstance client) {
        this.client = Checks.requireNonNullElse(client, Unirest.spawnInstance());
    }

    @Override
    public String load(URL url) throws IOException {
        HttpResponse<String> response = requestBuilder(client, url).asString();
        if (!response.isSuccess()) {
            throw new IOException("Server returned HTTP response code: " + response.getStatus());
        }
        return response.getBody();
    }

    protected HttpRequest<?> requestBuilder(UnirestInstance client, URL url) {
        return client.get(url.toString());
    }
}
