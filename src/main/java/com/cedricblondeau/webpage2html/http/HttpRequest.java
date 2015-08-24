package com.cedricblondeau.webpage2html.http;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HttpRequest {

    private static final Logger logger = Logger.getLogger(HttpRequest.class.getName());
    private OkHttpClient client = new OkHttpClient();
    private Request request;

    /**
     * @param url
     */
    public HttpRequest(URL url) {
        request = new Request.Builder().url(url).build();
    }

    /**
     * @return Response
     */
    public Response execute() {
        try {
            Response response = client.newCall(request).execute();
            return response;
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }
}
