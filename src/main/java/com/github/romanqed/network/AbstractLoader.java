package com.github.romanqed.network;


import com.github.romanqed.util.Checks;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public abstract class AbstractLoader implements Loader {
    protected OkHttpClient client;

    public AbstractLoader(OkHttpClient client) {
        this.client = Checks.requireNonNullElse(client, new OkHttpClient());
    }

    @Override
    public String load(URL url) throws IOException {
        Response response = client.newCall(requestBuilder(url)).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Server returned HTTP response code: " + response.code());
        }
        return Objects.requireNonNull(response.body()).string();
    }

    protected Request requestBuilder(URL url) {
        return new Request.Builder()
                .url(url)
                .build();
    }
}
