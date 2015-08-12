package com.cedricblondeau.webpage2html.http.resource;

import com.cedricblondeau.webpage2html.http.HttpCache;
import com.cedricblondeau.webpage2html.http.HttpRequest;
import com.squareup.okhttp.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;

public class HttpResourceFactory {

    public IHttpResource get(String resourceUrl, URL baseURL) {
        try {
            // Validate URL
            URL url = new URL(baseURL, resourceUrl);

            // Get HttpResource
            IHttpResource httpResource;
            if (HttpCache.getInstance().has(url.toExternalForm())) {
                httpResource = (IHttpResource) HttpCache.getInstance().get(url.toExternalForm());
            } else {
                HttpRequest httpRequest = new HttpRequest(url);
                ResponseBody responseBody = httpRequest.getResponse().body();
                httpResource = new HttpResource(responseBody, url);
            }
            return httpResource;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}