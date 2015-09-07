package com.cedricblondeau.webpage2html.http;

import com.cedricblondeau.webpage2html.Configuration;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;

public final class HttpRequest {

    private OkHttpClient client = new OkHttpClient();
    private Request request;

    /**
     * @param url
     * @param configuration
     */
    public HttpRequest(URL url, Configuration configuration) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (configuration.getUserAgent() != null && !configuration.getUserAgent().isEmpty()) {
            requestBuilder.addHeader("User-Agent", configuration.getUserAgent());
        }
        request = requestBuilder.build();
    }

    /**
     * @return Response
     */
    public Response execute() throws IOException {
        Response response = client.newCall(request).execute();
        return response;
    }
}
