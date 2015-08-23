package com.cedricblondeau.webpage2html.http;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;

public final class HttpRequest {

    private OkHttpClient client = new OkHttpClient();
    private Response response;

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
            System.out.println(e.getMessage());
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
