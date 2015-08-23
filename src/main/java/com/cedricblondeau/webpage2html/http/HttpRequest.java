package com.cedricblondeau.webpage2html.http;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class HttpRequest {

    private OkHttpClient client = new OkHttpClient();
    private Response response;
    private static final Logger logger = Logger.getLogger(HttpRequest.class.getName());

    /**
     * @param url
     */
    public HttpRequest(URL url) {
        response = execute(build(url));
    }

    /**
     * @param url
     * @return Request
     */
    private Request build(URL url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return request;
    }

    /**
     * @param request
     * @return Response
     */
    private Response execute(Request request) {
        try {
            Response response = client.newCall(request).execute();
            return response;
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    /**
     * @return Response
     */
    public Response getResponse() {
        return response;
    }
}
